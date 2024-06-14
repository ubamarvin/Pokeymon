package de.htwg.se.Pokeymon.Model.GameData
import de.htwg.se.Pokeymon.Model.GameData.Setup._
import de.htwg.se.Pokeymon.Model.GameData.Pokemon
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

//________________Class TRAINER______________________

/*
----@def removePokemon
    _pokemon: This represents the list of Pokémon.
    The expression _pokemon.filterNot(_ == pokemonToRemove) is a concise way of filtering out elements from the _pokemon list that are equal to pokemonToRemove. Here's how it works:
     .filterNot(...): This is a higher-order function applied to the _pokemon list. It takes a predicate function as an argument and returns a new list containing only the elements for which the predicate returns false.
    _ == pokemonToRemove: This is a shorthand notation for a function literal that compares each element of the _pokemon list with pokemonToRemove. Here, _ represents each individual element of the list, and pokemonToRemove is the element we want to remove.
    The expression _ == pokemonToRemove returns true if the current element (_) is equal to pokemonToRemove, and false otherwise.
 */
case class Trainer(pokemons: Vector[Pokemon], currentPokemon: Pokemon = evoli, choice: Option[Choice] = None): // Extend with Items
  val max_pokemon = 6

  def getPokemons(): Vector[Pokemon] =
    pokemons

  def getCurrentPokemon(): Pokemon =
    currentPokemon

  def removePokemon(pokemonName: String): Trainer =
    this.copy(pokemons = pokemons.filterNot(_.name == pokemonName))

  def setChoice(choice: Choice): Trainer = this.copy(choice = Some(choice))

  def addPokemon(new_pokemon: Pokemon): Trainer =
    this.copy(pokemons = pokemons :+ new_pokemon)

  // switchPokemon made this one obsolete
  def setCurrentPokemon(pokemon: Pokemon): Trainer =
    val upd_pokemons = pokemons.filterNot(_ == pokemon)
    this.copy(pokemons = upd_pokemons, currentPokemon = pokemon)

  // only used for the battle stuff
  def updateCurrentPokemon(pokemon: Pokemon): Trainer =
    this.copy(currentPokemon = pokemon)

  def getNextPokemon(): Pokemon = pokemons.head

  def pokemonExists(pokemonName: String): Boolean =
    pokemons.exists(_.name == pokemonName)

  // possible Bug: How is it ensured that the pokemons are consistent?
  def switchPokemon(pokemonToSwitchIn: Pokemon): Trainer =
    val upd_pokemons = pokemons.filterNot(_ == pokemonToSwitchIn) :+ currentPokemon
    this.copy(pokemons = upd_pokemons, currentPokemon = pokemonToSwitchIn)

  def getPokemonByName(name: String): Option[Pokemon] =
    pokemons.find(_.name.equalsIgnoreCase(name))

  def hasNoPokemonleft(): Boolean =
    (currentPokemon.hp <= 0 && pokemons.length == 0)

  override def toString: String =
    val pokemonStrings = pokemons.map(pokemon => s"${pokemon.name} (${pokemon.hp})")
    s" ${pokemonStrings.mkString(", ")}"
