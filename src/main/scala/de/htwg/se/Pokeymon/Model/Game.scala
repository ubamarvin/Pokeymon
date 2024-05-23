package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Setup
import de.htwg.se.Pokeymon.Model.Setup.opponent
import de.htwg.se.Pokeymon.Model.Setup.tackle

trait GameState {
  def processInput(input: String): GameState
  def gameToString(): String
}

// Context classssss
case class Game(val state: GameState = new PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent)) {

  // val player = Trainer(Vector())
  // val pokedex = new Pokedex()
  // val picks = 0
  // val opponent = Setup.opponent

  // Benutze ich nicht, wie auch
  // def setState(newState: GameState): Unit =
  //  state = newState

  // ist auch gleichzeitig state changer
  def handleInput(input: String): Game =
    val updstate = state.processInput(input)
    this.copy(updstate)

  def gameToString(): String =
    // println("gameToString is called")
    state.gameToString()

}

case class yourDeadState() extends GameState:
  override def processInput(Input: String): GameState =
    this.copy()

  override def gameToString(): String =
    "YourDead, stop clicking buttons, press q to quit"

case class PickPokemonState(player: Trainer, pokedex: Pokedex, picks: Int, opponent: Trainer) extends GameState:
  val eol: String = "\n"
  val welcome_msg = eol + eol + "Welcome to Pokemon BattleSimulator! " + eol +
    "Pick on to six Pokemon for you Team from the Pokedex." + eol +
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
case class MainState(player: Trainer, opponent: Trainer) extends GameState {
  override def gameToString(): String =
    display(player.currentPokemon, opponent.currentPokemon, player, opponent)
  override def processInput(input: String): GameState =
    // if input == attack then new attack state()
    // ....
    // ....
    input.toLowerCase() match {
      case "attack" => new ChooseAttackState(player, opponent)
      case "item"   => new ChooseItemState(player, opponent)
      case "switch" => new SwitchPokemonState(player, opponent)
      case "back"   => this
      case _        => this
    }

  private def display(pokemon1: Pokemon, pokemon2: Pokemon, player: Trainer, opponent: Trainer): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents current Pokemon: " + pokemon2.toString + eol + opponent.toString + eol,
      eol,
      "Your currents Pokemon: " + pokemon1.toString + eol + player.toString + eol +
        "What will you do? : Attack, Item, Switch"
    ).mkString
    middleRows
  }

}

case class BattleEvalState(player: Trainer, opponent: Trainer) extends GameState {
  // set OpponentsMove Choice
  val opMove: Option[Move] = Some(tackle)
  val upd_opponent = opponent.setChoice(new AttackChoice(opMove))

  val playersChoice = new PlayersChoice(player, upd_opponent, "")

  // Setting up the handlers
  val statusHandler = new StatusHandler()
  val evalAttackHandler = new EvaluateAttackHandler(statusHandler)
  val useItemHandler = new UseItemHandler(evalAttackHandler)
  val switchPokemonHandler = new SwitchPokemonHandler(useItemHandler) // or just call it Handler

  override def gameToString(): String = "BattleEvalState\n KlickEnter"
  // classic GameScreen
  override def processInput(input: String): GameState =
    // call handler
    val upd_playersChoice = switchPokemonHandler.handleChoice(playersChoice)

    new MainState(upd_playersChoice.player, upd_playersChoice.opponent)
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

  override def gameToString(): String = "\nattackChoiceState\nChoose Move"
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      case "back" => new MainState(player, opponent)

      case _ if MoveIsInList(moves, input.toLowerCase()) =>
        val move = getMoveByName(moves, input)
        val upd_player = player.setChoice(new AttackChoice(move))
        println("\nMove " + input + " accepted!")
        new BattleEvalState(upd_player, opponent)

      case _ => this
    }
    // if input == "go back"
    // then new GameState is battleMainstate
    // If Input == some Attack in Attack list
    // then upd Trainer with choice
    // then new Gamesatte is Battle eval stat
}

case class ChooseItemState(player: Trainer, opponent: Trainer) extends GameState {
  override def gameToString(): String = "ItemChoiceState\n Choose Item"
  override def processInput(input: String): GameState =
    input.toLowerCase() match {
      case "back" => new MainState(player, opponent)
      case "move" => new BattleEvalState(player, opponent)
      case _      => this
    }
    // if input == "go back"
    // then new GameState is battleMainstate
    // If Input == some Attack in Attack list
    // then upd Trainer with choice
    // then new Gamesatte is Battle eval state
}

case class SwitchPokemonState(player: Trainer, opponent: Trainer) extends GameState {
  override def gameToString(): String = "switchPokemonState\nChoose Pokemon"
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
    // if input == "go back"
    // then new GameState is battleMainstate
    // If Input == some Attack in Attack list
    // then upd Trainer with choice
    // then new Gamesatte is Battle eval state
}

// Break this class down into: Attack, Item, Switch State, MenuState, BattleEval
case class BattleState(player: Trainer, opponent: Trainer) extends GameState {

  override def gameToString(): String =
    display(player.currentPokemon, opponent.currentPokemon, player, opponent)

  private def display(pokemon1: Pokemon, pokemon2: Pokemon, player: Trainer, opponent: Trainer): String = {
    val eol: String = "\n"
    val middleRows = List(
      eol + eol +
        "Opponents current Pokemon: " + pokemon2.toString + eol + opponent.toString + eol,
      eol,
      "Your currents Pokemon: " + pokemon1.toString + eol + player.toString + eol +
        "Chose your Move:"
    ).mkString

    middleRows
  }
  // for def processInput with c o r pattern initialize the choices
  // this exist for proper choice evaluation
  // choices can obv only be made before passing them thru

  override def processInput(input: String): GameState =
    //// Start_______new Implementation using Chain o. Resp Pattern

    // 1. what if choice is switchpk or use item? Then i shouldnt be here?
    // should switchPokemonState and UseItemState also call the handlers?

    //// START______Old Implementation________
    val pok_with_move = player.currentPokemon.setCurrentMove(input.toLowerCase())
    val opp_with_move = opponent.currentPokemon.setCurrentMove("tackle")

    val (upd_player_pok, upd_opp_pok) = evaluateRound(pok_with_move, opp_with_move)
    val upd_player = player.setCurrentPokemon(upd_player_pok)
    val upd_opp = opponent.setCurrentPokemon(upd_opp_pok)

    val upd_player_alive = switchIfdead(upd_player)
    val upd_opponent_alive = switchIfdead(upd_opp)

    if (upd_player_alive.hasPokemonleft() && upd_opponent_alive.hasPokemonleft())
      this.copy(upd_player_alive, upd_opponent_alive)
    else
      new yourDeadState()
    //// END_______Old Implementation

  // HilfsMethoden
  def switchIfdead(trainer: Trainer): Trainer =
    val currentPokemon = trainer.currentPokemon
    if (!currentPokemon.isAlive() && trainer.pokemons.size > 1) {
      val trainer_rem = trainer.removePokemon(currentPokemon.name)
      val next_pokemon = trainer_rem.getNextPokemon()
      val upd_trainer = trainer_rem.setCurrentPokemon(next_pokemon)
      upd_trainer
    } else if (!currentPokemon.isAlive() && trainer.pokemons.size == 1) {

      val upd_Trainer = trainer.removePokemon(currentPokemon.name)
      upd_Trainer
    } else {
      trainer
    }

  def evaluateRound(playerPokemon: Pokemon, oponnentPokemon: Pokemon): (Pokemon, Pokemon) = {
    def matchPokemonById(pk1: Pokemon, pk2: Pokemon): (Pokemon, Pokemon) =
      val playerMon = if (pk1.id == playerPokemon.id) pk1 else pk2
      val oponnentMon = if (pk2.id == oponnentPokemon.id) pk2 else pk1
      (playerMon, oponnentMon)

    // Decide who executes Move first based on speed
    val first_mover = determineFasterPokemon(playerPokemon, oponnentPokemon)
    val secnd_mover = determineSlowerPokemon(playerPokemon, oponnentPokemon) // geht auch besser, wenns nicht a dann b // vergleich mit first mover

    // alternative kann man den pokemons auch ne current moveChoice geben
    // move Choice darf/kann/muss auch leer sein dürfen falls trainer itemt oder switched

    // Calculate Damage
    val upd_secnd_mover = secnd_mover.decreaseHp(first_mover.currentMove.power)
    // check if second mover dead and update the field!
    val upd_first_mover = first_mover.decreaseHp(secnd_mover.currentMove.power)

    // Copy updated pokemon back into back to correct Pokemon...this can be done outside too

    matchPokemonById(upd_first_mover, upd_secnd_mover) // returns (Pokemon,Pokemon)
  }

  def determineSlowerPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val slowerPokemon = if (pk1.speed <= pk2.speed) pk1 else pk2
    slowerPokemon

  def determineFasterPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val fasterPokemon = if (pk1.speed > pk2.speed) pk1 else pk2
    fasterPokemon

}
