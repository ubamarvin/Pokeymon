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
case class Trainer(pokemons: List[Pokemon]):
  def removePokemon(pokemonName: String): Trainer =
    this.copy(pokemons = pokemons.filterNot(_.name == pokemonName))

  def hasPokemonleft(): Boolean = !pokemons.isEmpty

case class Pokemon(name: String, hp: Int, moves: List[Move]):
  def decreaseHp(damage: Int): Pokemon = this.copy(hp = hp - damage)
  def isAlive(): Boolean = hp != 0
  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)

case class Move(name: String, power: Int)
//Generic Item class with traits i guess, or abstract class bs, so it finally
//comes in handy after all these years

//Item type potion class
case class Potion(name: String, power: Int)

val potion = Potion("potion", 30)

val tackle = Move("tackle", 50)
val thunder = Move("thunder", 70)
val pikachuMoves: List[Move] = List(tackle, thunder)
val pikachu = Pokemon("pikachu", 100, pikachuMoves)

val cowPokemon = Pokemon("cowPokemon", 100, List(tackle, thunder))

def battle(pk1: Pokemon, pk2: Pokemon): Unit =

  println("____________________" + pk2.name)
  println("____________________" + pk2.hp)
  println("____________________")
  println("____________________")
  println(pk1.name + "____________________")
  println(pk1.hp + "____________________")
  println(pk1.moves.foreach(move => move.name.mkString))
  print("\n\n\n")

  // Get Player(s) Move(s)
  val playerMove = scala.io.StdIn.readLine()

  // Evaluate Round
  val upd_pk2 = pk2.decreaseHp(pk1.attack(playerMove).getOrElse(0))
  val upd_pk1 = pk1.decreaseHp(pk2.attack("thunder").getOrElse(0))
  if (upd_pk1.hp <= 0 || upd_pk2.hp <= 0)
    println("Battle is over")
  else
    battle(upd_pk1, upd_pk2)

@main
def game(): Unit =
  battle(pikachu, cowPokemon)
