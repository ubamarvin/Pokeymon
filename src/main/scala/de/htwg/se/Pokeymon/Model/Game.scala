package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Setup
import de.htwg.se.Pokeymon.Model.Setup.opponent

trait GameState {
  def processInput(input: String): GameState
  def gameToString(): String
}

case class PickPokemonState(game: Game, player: Trainer, pokedex: Pokedex, picks: Int, opponent: Trainer) extends GameState:
  override def processInput(input: String): GameState =
    println("call pick your pokemon\n")

    val isPokemon = pokedex.exists(input)
    input.toLowerCase() match {
      case "d" if picks > 0 =>
        // Transition to new gameState
        println("TransitionTOBattle: input = d and picks>0")
        changeState(game, player, opponent)

      case _ if picks >= 6 =>
        // Transition to new gameState
        println("TransitionTOBattle: picks>=6")
        changeState(game, player, opponent)

      case _ =>
        if (!isPokemon) {
          println(input + "isNotAPokemon\n")
          this.copy(game, player, pokedex, picks, opponent)
        } else {
          println(input + " was added to your team!\n")
          val (picked_pokemon, upd_pokedex) = pokedex.choosePokemon(input)
          val upd_player = player.addPokemon(picked_pokemon)
          this.copy(game, upd_player, upd_pokedex, picks + 1, opponent)
        }

    }

  def changeState(game: Game, player: Trainer, opponent: Trainer): GameState =
    new BattleState(game, player.setCurrentPokemon(player.pokemons.head), opponent.setCurrentPokemon(opponent.pokemons.head))

  override def gameToString(): String = {
    println("state.pick.gameToString")
    val msg = "picked Pokemon Message"
    return msg
  }

case class BattleState(game: Game, player: Trainer, opponent: Trainer) extends GameState {

  override def gameToString(): String =
    "BattleStateString"
  override def processInput(input: String): GameState =
    println("battleState, choose move\n")
    // check if input is move
    val pok_with_move = player.currentPokemon.setCurrentMove(input.toLowerCase())
    val opp_with_move = opponent.currentPokemon.setCurrentMove("tackle")

    val (upd_player_pok, upd_opp_pok) = evaluateRound(pok_with_move, opp_with_move)
    val upd_player = player.setCurrentPokemon(upd_player_pok)
    val upd_opp = opponent.setCurrentPokemon(upd_opp_pok)

    val upd_player_alive = switchIfdead(upd_player)
    val upd_opponent_alive = switchIfdead(upd_opp)

    if (upd_player_alive.hasPokemonleft() && upd_opponent_alive.hasPokemonleft())
      this.copy(game, upd_player_alive, upd_opponent_alive)
    else
      this.copy()

    //  handle input for battle stuff

  // HilfsMethoden
  def switchIfdead(trainer: Trainer): Trainer =
    val currentPokemon = trainer.currentPokemon
    if (!currentPokemon.isAlive() && trainer.pokemons.size > 1) {
      val trainer_rem = trainer.removePokemon(currentPokemon.name)
      val next_pokemon = trainer_rem.getNextPokemon()
      val upd_trainer = trainer_rem.setCurrentPokemon(next_pokemon)
      upd_trainer
    } else if (!currentPokemon.isAlive() && trainer.pokemons.size == 1) {
      println("you have lost")
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

//  Context class
class Game {

  val player = Trainer(Vector())
  val pokedex = new Pokedex()
  val picks = 0
  val opponent = Setup.opponent
  private var state: GameState = new PickPokemonState(this, player, pokedex, picks, opponent)

  // Benutze ich nicht, wie auch
  def setState(newState: GameState): Unit =
    state = newState

  // ist auch gleichzeitig state changer
  def handleInput(input: String): Unit =
    state = state.processInput(input)

  def gameToString(): String =
    println("gameToString is called")
    state.gameToString()

}
