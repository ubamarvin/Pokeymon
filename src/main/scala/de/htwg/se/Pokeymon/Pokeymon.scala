import scala.util.Random

//________________Class TRAINER______________________

/*
----@def removePokemon
    _pokemon: This represents the list of Pokémon.
    The expression _pokemon.filterNot(_ == pokemonToRemove) is a concise way of filtering out elements from the _pokemon list that are equal to pokemonToRemove. Here's how it works:
     .filterNot(...): This is a higher-order function applied to the _pokemon list. It takes a predicate function as an argument and returns a new list containing only the elements for which the predicate returns false.
    _ == pokemonToRemove: This is a shorthand notation for a function literal that compares each element of the _pokemon list with pokemonToRemove. Here, _ represents each individual element of the list, and pokemonToRemove is the element we want to remove.
    The expression _ == pokemonToRemove returns true if the current element (_) is equal to pokemonToRemove, and false otherwise.
 */
case class Trainer(pokemons: Vector[Pokemon], currentPokemon: Pokemon = evoli): // Extend with Items
  val max_pokemon = 6
  def removePokemon(pokemonName: String): Trainer =
    this.copy(pokemons = pokemons.filterNot(_.name == pokemonName))
  def addPokemon(new_pokemon: Pokemon): Trainer =
    this.copy(pokemons = pokemons :+ new_pokemon)
  def setCurrentPokemon(pokemon: Pokemon): Trainer = this.copy(currentPokemon = pokemon)
  def getNextPokemon(): Pokemon = pokemons.head
  // These two function will help in putting and taking pokemon easily onto the trainers possesion, good exercise for the lists/vectors etc
  // def putPokemonOnBattle(name): Pokemon
  // def placePokemonInTrainer(name): Boolean, denn wenns mehr als 6 gehts nicht
  // val für current Pokemon?
  def hasPokemonleft(): Boolean = !pokemons.isEmpty
  override def toString: String =
    val pokemonStrings = pokemons.map(pokemon => s"${pokemon.name} (${pokemon.hp})")
    s"Trainer's Pokemons: ${pokemonStrings.mkString(", ")}"

//_____________Class Pokemon____________________
case class Pokemon(id: Int, name: String, hp: Int = 100, moves: List[Move], speed: Int, currentMove: Move = empty_move): // extendWith ID, Stats, type, and status
  def decreaseHp(damage: Int): Pokemon = this.copy(hp = hp - damage)
  def isAlive(): Boolean = hp > 0
  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)
  def setCurrentMove(new_move: String): Pokemon =
    val move = this.moves.find(_.name == new_move).getOrElse(empty_move)
    this.copy(currentMove = move)
  override def toString: String =
    val moveNames = moves.map(_.name).mkString(", ")
    s"$name, $hp, [$moveNames], $speed"

  // eine Attacke kann entweder ein Hp Move sein oder ein Status veränderndern Move sein, hier soll das geregelt werden
  // def receiveAttack(move)

  // def changeStatus

//___________Class Moves____________///
//extend with pp
//extend with type
//extend with executeMove()?
case class Move(
    name: String,
    power: Int
)

//***********Building a Trainer, Pokemons, and Moves
val empty_move = Move("empty", 0)
val tackle = Move("tackle", 50)
val thunder = Move("thunder", 70)
val bodyslam = Move("bodyslam", 30)
val kick = Move("kick", 10)

//***********Pokemons
val pikachu_moves: List[Move] = List(tackle, thunder)
val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30)

val rat_moves: List[Move] = List(bodyslam, kick)
val ratmon = Pokemon(2, "ratmon", 100, rat_moves, 40)

val cowPokemon = Pokemon(3, "cowpokemon", 100, List(tackle, thunder), 35)
val evoli = Pokemon(4, "evoli", 100, rat_moves, 35)

val trainer_ash = Trainer(Vector())

//**********Build Dummy opponent
val std_moves: List[Move] = List(tackle)
val testmon1 = Pokemon(5, "testmon1", 100, std_moves, 50)
val testmon2 = Pokemon(6, "testmon2", 100, std_moves, 50)
val testmon3 = Pokemon(7, "testmon3", 100, std_moves, 50)
val testmon4 = Pokemon(8, "testmon4", 100, std_moves, 50)
val testmon5 = Pokemon(9, "testmon5", 100, std_moves, 50)
val testmon6 = Pokemon(10, "testmon6", 100, std_moves, 50)
val std_mons = Vector(testmon1, testmon2, testmon3, testmon4, testmon5, testmon6)
val opponent = Trainer(std_mons)

//***********List of all available Pokemon
val available_pokemon = Vector(pikachu, ratmon, cowPokemon, evoli)
//_____________Class Pokedex_______________//
//This class handles all the available Pokemon
//The purpose of this class is to hand out a Pokemon to a Trainer
//@showAvailablePokemon returns String represantaion of all available Pokemon by name ToDo: Add rep by lvl,movepool,type etc
//@choosePokemon returns the @param name specified Pokemon and the new Pokedex reduced by that Pokemon
class Pokedex(available_pokemon: Vector[Pokemon] = available_pokemon):
  def showAvailablePokemon(): String = available_pokemon.map(_.name).mkString(", ")
  def choosePokemon(name: String): (Pokemon, Pokedex) =
    val (chosenPokemon, remainingPokemon) = available_pokemon.partition(_.name == name)
    val updatedPokedex = new Pokedex(remainingPokemon)
    (chosenPokemon.head, updatedPokedex)
  def exists(name: String): Boolean = available_pokemon.exists(_.name == name)

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

//keine globale funktionen
//@def pickYourPokemons
//this function handles the picking of Pokemons for a Trainer
//For testing purposes a prepared List of to be chosen Pokemon is passed in
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

//***********Trainers

//@def preapareForBattle()
//This function handles pre-Battle configurations:
//The player is able to choose one to six Pokemons from the Pokedex
//Todo: The Player is able to choose zero to four items from the Item Inventory
def prepareForBattle(player: Trainer = Trainer(Vector()), opponent: Trainer = opponent, pokedex: Pokedex = Pokedex()): String =

  val (upd_player, upd_pokedex, num_of_pokemons) = pickYourPokemons(player, pokedex)
  // def Build oppnent

  val upd_player_ready = upd_player.setCurrentPokemon(upd_player.pokemons.head)
  val opponent_ready = opponent.setCurrentPokemon(opponent.pokemons.head)

  battleMode(upd_player_ready, opponent_ready)
  println("game over")
  val msg = ""
  msg

//_______________battleMode
//Hier sollen die tatsächlichen Runden ausgehandelt werden
//diese funktion befasst sich nur mit des Stellen der Pokemon
// und der Wahl der Aktion (Move,Item,Switch)
def battleMode(player: Trainer, opponent: Trainer): String =

  val playerPokemon = player.currentPokemon

  val opponentPokemon = opponent.currentPokemon

  println(generatePokemonField(playerPokemon, opponentPokemon, player, opponent))

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
//faulty
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

//__________________evaluateRound()________________________________
///Wenn Trainer Item oder Pokemon switch gewählt hat, dann ist Move Choice Empty? Sollte dan überhaupt das alles gemacht werden?
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

//Damit man weiß, welches Pokemon zuerst zuschlagen darf, müssen die Geschwindigkeiten miteinander verglichen werden
//Später muss auch die Geschwindigkeit und Status mit einbezogen werden
def determineSlowerPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
  val slowerPokemon = if (pk1.speed <= pk2.speed) pk1 else pk2
  slowerPokemon

def determineFasterPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
  val fasterPokemon = if (pk1.speed > pk2.speed) pk1 else pk2
  fasterPokemon

@main
def game(): Unit =
  val pokedex = Pokedex()
  startGame()
  // println(pokedex.showAvailablePokemon())
  // pickYourPokemons(trainer_ash, pokedex)
  // println(pikachu.currentMove.name)
  // val upd = pikachu.setCurrentMove("tackle")
  // println(upd.currentMove.name)
