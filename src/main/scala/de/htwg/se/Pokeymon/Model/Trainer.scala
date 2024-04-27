package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Setup._
import de.htwg.se.Pokeymon.Model.Pokemon

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

  def hasPokemonleft(): Boolean = !pokemons.isEmpty

  override def toString: String =
    val pokemonStrings = pokemons.map(pokemon => s"${pokemon.name} (${pokemon.hp})")
    s"Trainer's Pokemons: ${pokemonStrings.mkString(", ")}"
