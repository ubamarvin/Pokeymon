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


trait GameState {
  def processInput(input: String): GameState
  def gameToString(): String
  }

// Context classssss
case class Game @Inject() (
    // BEFORE: val state: GameState = new PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent),
    val state: GameState,
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
    // printf("undo in game\n")
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

 
}

//_____________________________Dead
case class YourDeadState(player: Trainer, opponent: Trainer, roundReport: String) extends GameState:

  val winner = if player.hasNoPokemonleft() then "Opponent has" else "You have"

  override def processInput(input: String): GameState = {
    val result = Try {
      input.toLowerCase() match {
        case "ja"   => new PickPokemonState(Trainer(1,Vector()), Setup.pokedex, picks = 0, Setup.opponent)
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
  ///Pre-set opponents Choice
  val opMove: Option[Move] = Some(tackle)
  val upd_opponent = opponent.setChoice(new AttackChoice(opMove))
  val oppMonMoveSet = upd_opponent.currentPokemon.setCurrentMove("tackle")
  val readyOpp = upd_opponent.updateCurrentPokemon(oppMonMoveSet)


  override def gameToString(): String =
    display(player, readyOpp)
  override def processInput(input: String): GameState = {

    input.toLowerCase() match {
      case "attack" => new ChooseAttackState(player, readyOpp)
      case "item"   => new ChooseItemState(player, readyOpp)
      case "switch" => new SwitchPokemonState(player, readyOpp)
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
        new SwitchEvalState(ready_player, opponent)

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
  override def gameToString(): String = display(player, opponent)
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      case "back" => new MainState(player, opponent)
      case "move" => new SwitchEvalState(player, opponent)
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
        new SwitchEvalState(upd_player, opponent)

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


///Spread this state into those seen on the StateMachine Diagramm/*
case class BattleEvalState(player: Trainer, opponent: Trainer) extends GameState{

  override def gameToString(): String = 
    "."

  override def processInput(input: String): GameState = 
    new MainState(player, opponent)


}


//prev States: Choose A S I States
case class SwitchEvalState(player: Trainer, opponent: Trainer) extends GameState {
  println("SwitchEvalState");


  def calcNextState(): GameState = {
    val playerChoice = player.choice;
    val opponentChoice = opponent.choice;

    (playerChoice, opponentChoice) match {
      // Both players switch their Pokémon
      case (Some(pc: SwitchPokemonChoice), Some(oc: SwitchPokemonChoice)) =>
        println("both switch")
        (pc.pokemon, oc.pokemon) match {
          case (Some(playerPokemon), Some(opponentPokemon)) =>
            new SwitchPlayerState(player, opponent);
          case _ =>
            println("Error in SwitchEval1")
            new MainState(player, opponent);
        }

      // Same returnValue as 1
      // Only the player switches their Pokémon
      case (Some(pc: SwitchPokemonChoice), _) =>
        println("player switches")
        pc.pokemon match {
          case Some(playerPokemon) =>
            new SwitchPlayerState(player, opponent);
          case None =>
            println("Error in SwitchEval2")
            new MainState(player, opponent);
        }

      // Only the opponent switches their Pokémon
      case (_, Some(oc: SwitchPokemonChoice)) =>
        println("opponent switches")
        oc.pokemon match {
          case Some(opponentPokemon) =>
              new SwitchOpponentState(player, opponent);
          case None =>
            println("Error in SwitchEval3")
            new MainState(player, opponent);
        }

      // No switch, pass to the next handler in the chain
      case _ =>
        new ItemEvalState(player, opponent);     
    }
  }
 



  override def gameToString(): String = 
    "SwitchEvalCase"

  override def processInput(input: String): GameState = 
    calcNextState();

  
}

case class SwitchPlayerState(player: Trainer, opponent: Trainer) extends GameState {
   def switchPokemon(): GameState = {
      // switch pokemon out
      // Typsicherheit?
      val nextPokemon = player.choice match {
        case Some(SwitchPokemonChoice(Some(pokemon))) => pokemon
        case _ => player.currentPokemon
      }
      val upd_player = player.switchPokemon(nextPokemon);
      println("-Player chooses " + nextPokemon.name + " to fight!\n")
      //check what next state is
      (opponent.choice) match {
        case Some(oc: SwitchPokemonChoice) =>
          oc.pokemon match {
            case Some(opponentPokemon) =>
              new SwitchOpponentState(upd_player, opponent)
            case None => 
              println("opponent doesnt want to switch");
              new ItemEvalState(upd_player, opponent);
          }
        case _ => new ItemEvalState(upd_player, opponent);
      }
      // Opponent doesnt wish to change pk -> item state
      new ItemEvalState(upd_player, opponent)
   }


  override def gameToString(): String = 
    "player chooses Pokemon to fight"

  override def processInput(input: String): GameState = switchPokemon()

  
}

case class SwitchOpponentState(player: Trainer, opponent: Trainer) extends GameState {

  def switchPokemon(): GameState = {
    val nextPokemon = opponent.choice match {
        case Some(SwitchPokemonChoice(Some(pokemon))) => pokemon
        case _ => opponent.currentPokemon
      }
    val updOpp = opponent.switchPokemon(nextPokemon);
    new ItemEvalState(player, updOpp);
  }


  override def gameToString(): String = 
    "opponent chooses to change pk"

  override def processInput(input: String): GameState = switchPokemon();

  
}

//prev State Switch
case class ItemEvalState(player: Trainer, opponent: Trainer) extends GameState {



  override def gameToString(): String = 
    "Item eval"

  override def processInput(input: String): GameState = new MainState(player, opponent)
  
}
/*
case class ItemPlayerState(player: Trainer, opponent: Trainer) extends GameState {

 

  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class ItemOpponentState(player: Trainer, opponent: Trainer) extends GameState {



  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class AttackEvalState(player: Trainer, opponent: Trainer) extends GameState {


  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class AttackFirstMoverState(player: Trainer, opponent: Trainer) extends GameState {

  
  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class AttackSecondMoverState(player: Trainer, opponent: Trainer) extends GameState {

  

  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class StatusEvalState(player: Trainer, opponent: Trainer) extends GameState {

  
  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class StatusPlayerState(player: Trainer, opponent: Trainer) extends GameState {

  

  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

case class StatusOpponentState(player: Trainer, opponent: Trainer) extends GameState {

  
  override def gameToString(): String = ???

  override def processInput(input: String): GameState = ???

  
}

*/