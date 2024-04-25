import scala.util.Random

//------------CLASS TRAINER---------------

/*
----@def removePokemon
    _pokemon: This represents the list of Pokémon.
    The expression _pokemon.filterNot(_ == pokemonToRemove) is a concise way of filtering out elements from the _pokemon list that are equal to pokemonToRemove. Here's how it works:
     .filterNot(...): This is a higher-order function applied to the _pokemon list. It takes a predicate function as an argument and returns a new list containing only the elements for which the predicate returns false.
    _ == pokemonToRemove: This is a shorthand notation for a function literal that compares each element of the _pokemon list with pokemonToRemove. Here, _ represents each individual element of the list, and pokemonToRemove is the element we want to remove.
    The expression _ == pokemonToRemove returns true if the current element (_) is equal to pokemonToRemove, and false otherwise.
 */
case class Trainer(pokemons: List[Pokemon]): // Extend with Items
  val max_pokemon = 6
  def removePokemon(pokemonName: String): Trainer =
    this.copy(pokemons = pokemons.filterNot(_.name == pokemonName))
  // These two function will help in putting and taking pokemon easily onto the trainers possesion, good exercise for the lists/vectors etc
  // def putPokemonOnBattle(name): Pokemon
  // def placePokemonInTrainer(name): Boolean, denn wenns mehr als 6 gehts nicht
  // val für current Pokemon?
  def hasPokemonleft(): Boolean = !pokemons.isEmpty

//------------CLASS Pokemon---------------
case class Pokemon(id: Int, name: String, hp: Int = 100, moves: List[Move], speed: Int, currentMove: Move = empty): // extendWith ID, Stats, type, and status
  def decreaseHp(damage: Int): Pokemon = this.copy(hp = hp - damage)
  def isAlive(): Boolean = hp != 0
  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)
  def setCurrentMove(currentMove: Move): Pokemon = this.copy(currentMove = currentMove) //

  // Damit bei der übergabe des Pokemons die gwählte Attacke nicht überkompliziert ermittelt werden muss
  // wird sie hier gespeichert
  // def setCurrentMove()
  // Move muss natürlich im Move Pool vorhanden sein

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
val empty = Move("empty", 0)
val tackle = Move("tackle", 50)
val thunder = Move("thunder", 70)
val bodyslam = Move("bodyslam", 30)
val kick = Move("kick", 10)

//***********Pokemons
val pikachu_moves: List[Move] = List(tackle, thunder)
val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30)

val rat_moves: List[Move] = List(bodyslam, kick)
val ratmon = Pokemon(2, "ratmon", 100, rat_moves, 40)

val cowPokemon = Pokemon(3, "cowPokemon", 100, List(tackle, thunder), 35)
val evoli = Pokemon(4, "evoli", 100, rat_moves, 35)

//***********List of all available Pokemon
val available_pokemon = Vector(pikachu, ratmon, cowPokemon, evoli)
class Pokedex(available_pokemon: Vector[Pokemon] = available_pokemon):
  def showAvailablePokemon(): String = available_pokemon.map(_.name).mkString(", ")
  // def choosePokemon(name: String): (Pokemon, Pokedex) =

//***********Trainers
val trainer_ash = Trainer(List(pikachu, ratmon))
val trainer_gary = Trainer(List(cowPokemon, evoli))

//_______________preapareForBattle()___________________-
//Hier sollen pre Battle Einstellungen gewählt werden
//Der Spieler soll dazu aufgefordet werden 1-6 Pokemon zu wählen
//und 0-4 Items
//Zu jedem Wählbaren PokeyMun wird es eine kleine Übersicht geben aka to String
def prepareForBattle(trainer1: Trainer, trainer2: Trainer): String =
  // Choose Pokeymons, def pickPokemon, pickPokemon from limited set of Pokemon
  // SetOfPokemon with distinct id,  first Pokemon to choose is leading, max choose == 6

  // Choose Items,     def pickItems, Max item == 4

  // confirm your fuckin choice or redoo the whole fuckin thing

  // battle()
  val msg = "fuck"
  msg

//_______________battleMode
//Hier sollen die tatsächlichen Runden ausgehandelt werden
//diese funktion befasst sich nur mit des Stellen der Pokemon
// und der Wahl der Aktion (Move,Item,Switch)
def battleMode(trainer1: Trainer, trainer2: Trainer): String =
  // 0 1 2 Round what would i print Pick your move
  val promptMessage = pickYourMove()
  // Trainer1CurrentPokemon = List Head oder so ähnlich
  // Trainer2CurrentPokemon =
  // Wenn ich Aktion Item oder Pokemon switchen wähle, wird diese zu erst ausgeführt
  // ItemUsage and switchPokemon will always be executed first if choosen!!

  // Maybe Pk1 attack pk2 directly wenn pk 2 item gewählt hat?

  // updatedPokemons = evaluateRound()
  // parse Pokemons via id back into trainer def orderPokemonToTrainerbyId

  // update the field and print it
  // ending message falls eins dead is, sonst
  // battleMode()
  val msg = "fuck"
  msg

//__________________evaluateRound()________________________________
///Wenn Trainer Item oder Pokemon switch gewählt hat, dann ist Move Choice Empty? Sollte dan überhaupt das alles gemacht werden?
// einfach nur def attack im falle einer nur einseitigen attacke
def evaluateRound(pk1: Pokemon, move_choice_1: String, pk2: Pokemon, move_choice_2: String): List[Pokemon] = {

  // Decide who executes Move first based on speed
  val first_mover = determineFasterPokemon(pk1, pk2)
  val scnd_mover = determineSlowerPokemon(pk1, pk2) // geht auch besser, wenns nicht a dann b // vergleich mit first mover

  // alternative kann man den pokemons auch ne current moveChoice geben
  // move Choice darf/kann/muss auch leer sein dürfen falls trainer itemt oder switched
  val first_mover_move_choice = if (first_mover.id == pk1.id) move_choice_1 else move_choice_2
  val scnd_mover_move_choice = if (scnd_mover.id == pk2.id) move_choice_2 else move_choice_1
  // Calculate Damage
  val updated_scnd_mover = scnd_mover.decreaseHp(first_mover.attack(first_mover_move_choice).getOrElse(0))
  // check if second mover dead and update the field!
  val updated_first_mover = first_mover.decreaseHp(scnd_mover.attack(scnd_mover_move_choice).getOrElse(0))

  // Copy updated pokemon back into back to correct Pokemon...this can be done outside too
  val updated_pk1 = if (updated_first_mover.id == pk1.id) updated_first_mover else updated_scnd_mover
  val updated_pk2 = if (updated_scnd_mover.id == pk2.id) updated_scnd_mover else updated_first_mover

  val pokemonList: List[Pokemon] = List(updated_pk1, updated_pk1)
  pokemonList
}

//Damit man weiß, welches Pokemon zuerst zuschlagen darf, müssen die Geschwindigkeiten miteinander verglichen werden
//Später muss auch die Geschwindigkeit und Status mit einbezogen werden
def determineSlowerPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
  val slowerPokemon = if (pk1.speed <= pk2.speed) pk1 else pk2
  slowerPokemon

def determineFasterPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
  val fasterPokemon = if (pk1.speed > pk2.speed) pk1 else pk2
  fasterPokemon

def pickYourMove(): String =
  val msg = "What will you do?"
  msg

@main
def game(): Unit =
  val pokedex = Pokedex()
  println(pokedex.showAvailablePokemon())
