package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Setup._

class GameField:
  // ___CONTROLLER?
  def generatePokemonField(pokemon1: Pokemon, pokemon2: Pokemon, player: Trainer, opponent: Trainer): String = {
    // val horizontalBorder = "+" + ("-" * 30) + "+\n"
    // val verticalBorder = "|"
    val eol: String = "\n"

    // val topBorder = horizontalBorder
    // val bottomBorder = horizontalBorder

    val middleRows = List(
      "Opponents current Pokemon: " + pokemon2.toString + eol + opponent.toString + eol,
      eol,
      "Your currents Pokemon: " + pokemon1.toString + eol + player.toString + eol
    ).mkString

    middleRows
  }
  // State 1
  // ____CONTROLLER? It manipulates data?
  // this function handles the picking of Pokemons for a Trainer
  // For testing purposes a prepared List of to be chosen Pokemon is passed in
  def pickYourPokemons(player: Trainer, pokedex: Pokedex, picks: Int = 0): (Trainer, Pokedex, Int) =
    println("\nAvailable Pokemon: " + pokedex.showAvailablePokemon())
    val player_input = scala.io.StdIn.readLine("Choose Pokemon, press d for done \n")
    val is_pokemon = pokedex.exists(player_input.toLowerCase())
    player_input.toLowerCase() match {

      case "d" if picks > 0 => (player, pokedex, picks)

      case "d" =>
        println("must choose atleast one Pokeymon")
        pickYourPokemons(player, pokedex, picks = 0)

      case _ if picks >= 6 =>
        println("Cant choose more than six Pokemons")
        (player, pokedex, picks)

      case _ =>
        if !is_pokemon then
          println("choice doesnt exist")
          pickYourPokemons(player, pokedex, picks)
        else
          println(player_input + " was added to your team!")
          val (picked_pokemon, upd_pokedex) = pokedex.choosePokemon(player_input)
          val upd_player = player.addPokemon(picked_pokemon)
          pickYourPokemons(upd_player, upd_pokedex, picks + 1)

      // case _ if !is_pokemon && picks > 0 =>
      //  println("Choice doesnt exist")
      //  pickYourPokemons(player, pokedex, picks)
      // case _ if is_pokemon && picks > 0 =>
      //  println("Your choice will be added")
      //  val (picked_pokemon, upd_pokedex) = pokedex.choosePokemon(player_input)
      //  val upd_player = player.addPokemon(picked_pokemon)
      //  pickYourPokemons(upd_player, upd_pokedex, picks + 1)

    }
  def startGame(): Unit =
    prepareForBattle()

  // ***********Trainers

  // State 0
  // @def preapareForBattle() rename to game, this fun has two "states"
  // This function handles pre-Battle configurations:
  // The player is able to choose one to six Pokemons from the Pokedex
  // Todo: The Player is able to choose zero to four items from the Item Inventory
  def prepareForBattle(player: Trainer = Trainer(Vector()), opponent: Trainer = opponent, pokedex: Pokedex = Pokedex()): String =

    val (upd_player, upd_pokedex, num_of_pokemons) = pickYourPokemons(player, pokedex)
    // def Build oppnent

    val upd_player_ready = upd_player.setCurrentPokemon(upd_player.pokemons.head)
    val opponent_ready = opponent.setCurrentPokemon(opponent.pokemons.head)

    battleMode(upd_player_ready, opponent_ready)
    println("game over")
    val msg = ""
    msg

    // State 2
  // _______________battleMode
  // Hier sollen die tatsächlichen Runden ausgehandelt werden
  // diese funktion befasst sich nur mit des Stellen der Pokemon
  // und der Wahl der Aktion (Move,Item,Switch)
  def battleMode(player: Trainer, opponent: Trainer): String =

    val playerPokemon = player.currentPokemon

    val opponentPokemon = opponent.currentPokemon

    println(generatePokemonField(playerPokemon, opponentPokemon, player, opponent))

    // INPUT
    val player_move = scala.io.StdIn.readLine("Choose a move\n") // def chooseMove(pk:Pokemon): Move
    val playerPokemon_move_is_set = playerPokemon.setCurrentMove(player_move.toLowerCase())

    val opponentPokemon_move_is_set = opponentPokemon.setCurrentMove("tackle")

    val (upd_playerPokemon, upd_opponentPokemon) = evaluateRound(playerPokemon_move_is_set, opponentPokemon_move_is_set)

    val upd_player = player.setCurrentPokemon(upd_playerPokemon)
    val upd_opponent = opponent.setCurrentPokemon(upd_opponentPokemon)

    val upd_player_alive = switchIfdead(upd_player)
    val upd_opponent_alive = switchIfdead(upd_opponent)
    // println(s"Player has Pokemon left: ${upd_player_alive.hasPokemonleft()}")
    // println(s"Opponent has Pokemon left: ${upd_opponent_alive.hasPokemonleft()}")
    if (upd_player_alive.hasPokemonleft() && upd_opponent_alive.hasPokemonleft())
      battleMode(upd_player_alive, upd_opponent_alive)

    println("")

    val msg = ""
    msg
  // belongs to battle
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

    // belongs to battle
  // __________________evaluateRound()________________________________
  /// Wenn Trainer Item oder Pokemon switch gewählt hat, dann ist Move Choice Empty? Sollte dan überhaupt das alles gemacht werden?
  // einfach nur def attack im falle einer nur einseitigen attacke
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

  // belongs to battle
  // Damit man weiß, welches Pokemon zuerst zuschlagen darf, müssen die Geschwindigkeiten miteinander verglichen werden
  // Später muss auch die Geschwindigkeit und Status mit einbezogen werden
  def determineSlowerPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val slowerPokemon = if (pk1.speed <= pk2.speed) pk1 else pk2
    slowerPokemon

  def determineFasterPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val fasterPokemon = if (pk1.speed > pk2.speed) pk1 else pk2
    fasterPokemon
