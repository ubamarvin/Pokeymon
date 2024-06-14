package de.htwg.se.Pokeymon.Model.GameData
import de.htwg.se.Pokeymon.Model.GameData.Setup._
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

//_____________Class Pokedex_______________//
//This class handles all the available Pokemon
//The purpose of this class is to hand out a Pokemon to a Trainer
//@showAvailablePokemon returns String represantaion of all available Pokemon by name ToDo: Add rep by lvl,movepool,type etc
//@choosePokemon returns the @param name specified Pokemon and the new Pokedex reduced by that Pokemon
class Pokedex(val available_pokemon: Vector[Pokemon] = available_pokemon):

  def showAvailablePokemon(): String = available_pokemon.map(_.name).mkString(", ")

  def choosePokemon(name: String): (Pokemon, Pokedex) =
    val (chosenPokemon, remainingPokemon) = available_pokemon.partition(_.name == name)
    val updatedPokedex = new Pokedex(remainingPokemon)
    (chosenPokemon.head, updatedPokedex)

  def exists(name: String): Boolean = available_pokemon.exists(_.name == name)
