package de.htwg.se.Pokeymon.Model.GameComponent
import de.htwg.se.Pokeymon.Model.GameData.Setup
import de.htwg.se.Pokeymon.Model.GameData.Setup.opponent
import de.htwg.se.Pokeymon.Model.GameData.Setup.tackle
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._
import com.google.inject.Inject
import com.google.inject.name.Named
import scala.collection.immutable.Vector

import scala.util.{Try, Success, Failure}

//case class Content(state: String = " ", player: Trainer, opponent: Trainer, msg: String = "", pokedex: Pokedex)
//_______________Content for Gui___________________________//
//trait Content

case class Content(state: String, player: Trainer, opponent: Trainer, roundReport: String = "", pokedex: Pokedex = Setup.pokedex)
trait GameState {
  def processInput(input: String): GameState
  def gameToString(): String
  def getContent(): Content
}

// Context classssss
case class Game @Inject() (
    val state: GameState,
    // val state: GameState = new PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent),
    val undoStack: Vector[GameState] = Vector.empty,
    val redoStack: Vector[GameState] = Vector.empty
) extends GameInterface {

  // Handles, input, changesState and updates the StateStack
  def handleInput(input: String): Game =
    val NextState = state.processInput(input)
    val updUndoStack = state +: undoStack
    this.copy(NextState, updUndoStack)
    // this.copy(state = NextState)

  // for Memento and Command, specifically for und
  // Undo operation
  // Undo operation
  def gameUndo(): Game = {
    printf("undo in game\n")
    if (undoStack.isEmpty) {
      this
    } else {
      val prevState +: rest = undoStack: @unchecked
      this.copy(state = prevState, undoStack = rest, redoStack = state +: redoStack)
    }
  }

  // Redo operation
  def gameRedo(): Game = {
    if (redoStack.isEmpty) {
      this
    } else {
      val nextState +: rest = redoStack: @unchecked
      this.copy(state = nextState, undoStack = state +: undoStack, redoStack = rest)
    }
  }

  def gameToString(): String =
    // println("gameToString is called")
    state.gameToString()

  def getContent(): Content =
    state.getContent()

}

//_____________________________Dead
case class YourDeadState(player: Trainer, opponent: Trainer, roundReport: String) extends GameState:

  val winner = if player.hasNoPokemonleft() then "Opponent has" else "You have"

  override def processInput(input: String): GameState = {
    val result = Try {
      input.toLowerCase() match {
        case "ja"   => new PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent)
        case "n"    => this
        case "fail" => throw new RuntimeException("Simulated failure") // Simulated failure case
        case _      => this
      }
    }

    result match {
      case Success(tomate) => tomate
      case Failure(exception) =>
        println(s"Ein Fehler ist passiert: ${exception.getMessage}")
        this
    }
  }
  override def getContent(): Content =
    new Content("dead", player, opponent, roundReport)

  override def gameToString(): String = {
    val result = Try {
      display(player, opponent, roundReport)
    }

    result match {
      case Success(gameString) => gameString
      case Failure(exception) =>
        println(s"An error occurred while generating game string: ${exception.getMessage}")
        "An error occurred while displaying the game state."
    }
  }

  private def display(player: Trainer, opponent: Trainer, msg: String): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents Pokemon: " + opponent.currentPokemon.toString + eol + opponent.toString + eol,
      eol,
      "Players Pokemon: " + player.currentPokemon.toString + eol + player.toString + eol + msg + eol +
        winner + " won this Round!!!! \n Play again? ja/n"
    ).mkString
    middleRows
  }

//_____________________Pick
case class PickPokemonState(player: Trainer, pokedex: Pokedex, picks: Int, opponent: Trainer) extends GameState:
  val eol: String = "\n"
  val welcome_msg = eol + eol + "Welcome to Pokemon BattleSimulator! " + eol +
    "Pick 1-6 Pokemon." + eol +
    "When your done with picking, press d." + eol +
    "If you want to quit the game, press q." + eol +
    "Good luck" + eol +
    eol + "The available Pokemon are: "

  val choose_msg = eol + "type in the name of the pokemon you wish to add to your team:"

  override def getContent(): Content =
    new Content("pick", player, opponent, "", pokedex)
  override def gameToString(): String = {
    display(player, pokedex, picks)
  }
  private def display(player: Trainer, pokedex: Pokedex, picks: Int): String =
    picks match {
      case 0               => welcome_msg + pokedex.showAvailablePokemon() + "\n" + choose_msg
      case _ if picks >= 6 => "Game Starts!\n"
      case _               => "Your team consits of " + player.toString + ". \nremaining to choose: " + pokedex.showAvailablePokemon() + "\n" + choose_msg
    }

  override def processInput(input: String): GameState =
    // println("call pick your pokemon\n")

    val isPokemon = pokedex.exists(input)
    input.toLowerCase() match {
      case "d" if picks > 0 =>
        // Transition to new gameState
        // println("TransitionTOBattle: input = d and picks>0")
        changeState(player, opponent)

      case _ if picks >= 6 =>
        // Transition to new gameState
        // println("TransitionTOBattle: picks>=6")
        changeState(player, opponent)

      case _ =>
        if (!isPokemon) {
          println(input + "isNotAPokemon\n")
          this.copy(player, pokedex, picks, opponent)
        } else {
          println(input + " was added to your team!\n")
          val (picked_pokemon, upd_pokedex) = pokedex.choosePokemon(input)
          val upd_player = player.addPokemon(picked_pokemon)
          this.copy(upd_player, upd_pokedex, picks + 1, opponent)
        }

    }

  // def changeState(player: Trainer, opponent: Trainer): GameState =
  //  new BattleState(player.setCurrentPokemon(player.pokemons.head), opponent.setCurrentPokemon(opponent.pokemons.head))

  def changeState(player: Trainer, opponent: Trainer): GameState =
    new MainState(player.setCurrentPokemon(player.pokemons.head), opponent.setCurrentPokemon(opponent.pokemons.head))

//_________Actuall "game" starts here

// This is the mainState
// here the player is prompted to either attack, use an item or switch his Pokemon
case class MainState(player: Trainer, opponent: Trainer, roundReport: String = "") extends GameState {

  override def getContent(): Content =
    new Content("main", player, opponent, roundReport)
  override def gameToString(): String =
    display(player, opponent)
  override def processInput(input: String): GameState = {

    input.toLowerCase() match {
      case "attack" => new ChooseAttackState(player, opponent)
      case "item"   => new ChooseItemState(player, opponent)
      case "switch" => new SwitchPokemonState(player, opponent)
      case "back"   => this
      case _        => this
    }
  }

  private def display(player: Trainer, opponent: Trainer, msg: String = roundReport): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents Pokemon: " + opponent.currentPokemon.toString + eol + opponent.toString + eol,
      eol,
      "Players Pokemon: " + player.currentPokemon.toString + eol + player.toString + eol + msg + eol +
        "What will you do? : Attack, Item, Switch"
    ).mkString
    middleRows
  }

}

case class BattleEvalState(player: Trainer, opponent: Trainer) extends GameState {
  // set OpponentsMove Choice
  val opMove: Option[Move] = Some(tackle)
  val upd_opponent = opponent.setChoice(new AttackChoice(opMove))
  val oppMonMoveSet = upd_opponent.currentPokemon.setCurrentMove("tackle")
  val readyOpp = upd_opponent.updateCurrentPokemon(oppMonMoveSet)

  val playersChoice = new PlayersChoice(player, readyOpp, "")

  // Setting up the handlers
  val statusHandler = new StatusHandler()
  val evalAttackHandler = new EvaluateAttackHandler(statusHandler)
  val useItemHandler = new UseItemHandler(evalAttackHandler)
  val switchPokemonHandler = new SwitchPokemonHandler(useItemHandler) // or just call it Handler

  override def getContent(): Content =
    new Content("battle", player, opponent)
  override def gameToString(): String = "are you sure? "
  // classic GameScreen
  override def processInput(input: String): GameState =
    // call handler
    // add upd_playerchoice to "memento"
    val upd_playersChoice = switchPokemonHandler.handleChoice(playersChoice)
    val roundReport = upd_playersChoice.roundReport
    val upd_player = upd_playersChoice.player
    val upd_opp = upd_playersChoice.opponent
    val newState =
      if (upd_player.hasNoPokemonleft() || upd_opp.hasNoPokemonleft()) then new YourDeadState(upd_player, upd_opp, roundReport)
      else new MainState(upd_playersChoice.player, upd_playersChoice.opponent, roundReport)

    if (upd_playersChoice.player.hasNoPokemonleft()) then println("playerLost")

    // new MainState(upd_playersChoice.player, upd_playersChoice.opponent, roundReport)
    newState
}

// These 3 states represent the 3 main Options when battling
// attack, item, switch pokemon
// each state allows either going back to main state
// or making a choice which will automatically
// push the upd player into eval state

case class ChooseAttackState(player: Trainer, opponent: Trainer) extends GameState {
  val moves = player.currentPokemon.moves
  def MoveIsInList(moves: List[Move], moveName: String): Boolean =
    moves.exists(_.name.equalsIgnoreCase(moveName))

  def getMoveByName(moves: List[Move], moveName: String): Option[Move] =
    moves.find(_.name.equalsIgnoreCase(moveName))

  def getContent(): Content =
    new Content("attack", player, opponent)

  override def gameToString(): String = display(player, opponent)
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      case "back" => new MainState(player, opponent)

      case _ if MoveIsInList(moves, input.toLowerCase()) =>
        val move = getMoveByName(moves, input)
        val PokemonMoveSet = player.currentPokemon.setCurrentMove(input)
        val upd_player = player.setChoice(new AttackChoice(move))
        val ready_player = upd_player.updateCurrentPokemon(PokemonMoveSet)
        println("\nMove " + input + " accepted!")
        new BattleEvalState(ready_player, opponent)

      case _ => this
    }

  private def display(player: Trainer, opponent: Trainer): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents Pokemon: " + opponent.currentPokemon.toString + eol + opponent.toString + eol,
      eol,
      "Players Pokemon: " + player.currentPokemon.toString + eol + player.toString + eol + eol +
        player.currentPokemon.movesToString()
    ).mkString
    middleRows
  }

}

case class ChooseItemState(player: Trainer, opponent: Trainer) extends GameState {
  def getContent(): Content =
    new Content("item", player, opponent)
  override def gameToString(): String = display(player, opponent)
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      case "back" => new MainState(player, opponent)
      case "move" => new BattleEvalState(player, opponent)
      case _      => this
    }
  private def display(player: Trainer, opponent: Trainer): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents Pokemon: " + opponent.currentPokemon.toString + eol + opponent.toString + eol,
      eol,
      "Players Pokemon: " + player.currentPokemon.toString + eol + player.toString + eol + eol +
        "Choose Item: " + "no items available"
    ).mkString
    middleRows
  }

}

case class SwitchPokemonState(player: Trainer, opponent: Trainer) extends GameState {
  def getContent(): Content =
    new Content("switch", player, opponent)
  override def gameToString(): String = display(player, opponent)
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      // going back to the main battle menu
      case "back" => new MainState(player, opponent)

      // player chooses pokemon to switch in
      case _ if player.pokemonExists(input.toLowerCase()) =>
        // get the Pokemon and update the playerChoice
        val pokemonToSwitchIn = player.getPokemonByName(input.toLowerCase())
        val upd_player = player.setChoice(new SwitchPokemonChoice(pokemonToSwitchIn))
        // call  Battle eval
        new BattleEvalState(upd_player, opponent)

      case _ => this

    }
  private def display(player: Trainer, opponent: Trainer): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents Pokemon: " + opponent.currentPokemon.toString + eol + opponent.toString + eol,
      eol,
      "Players Pokemon: " + player.currentPokemon.toString + eol + player.toString + eol + eol +
        "Choose: " + player.toString
    ).mkString
    middleRows
  }
  def getPreviousState(): GameState =
    this
  def getNextState(): GameState =
    this
}
