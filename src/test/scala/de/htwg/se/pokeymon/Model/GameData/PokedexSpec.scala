package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData.Setup._

class PokedexSpec extends AnyFlatSpec with Matchers {

  "A Pokedex" should "show available Pokemon correctly" in {
    val pokedex = new Pokedex(available_pokemon)
    val pokemonList = pokedex.showAvailablePokemon()
    pokemonList shouldEqual "pikachu, ratmon, cowpokemon, evoli, fish, firefox"
  }

  it should "choose a Pokemon correctly and update the Pokedex" in {
    val pokedex = new Pokedex(available_pokemon)
    val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("fish")
    chosenPokemon.name shouldEqual "fish"
    updatedPokedex.exists("fish") shouldBe false
  }

  it should "check if a Pokemon exists correctly" in {
    val pokedex = new Pokedex(available_pokemon)
    pokedex.exists("fish") shouldBe true
    pokedex.exists("nonExistentPokemon") shouldBe false
  }

}
