package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PokedexSpec extends AnyWordSpec with Matchers {

  "A Pokedex" when {
    "initialized with available Pokémon" should {
      val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunder Shock", 40, "electric")), 30, "electric")
      val bulbasaur = Pokemon(2, "Bulbasaur", 100, List(Move("Vine Whip", 45, "grass")), 30, "grass")
      val availablePokemon = Vector(pikachu, bulbasaur)
      val pokedex = new Pokedex(availablePokemon)

      "show the available Pokémon correctly" in {
        pokedex.showAvailablePokemon() should be("Pikachu, Bulbasaur")
      }

      "choose a Pokémon and update the Pokedex" in {
        val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("Pikachu")
        chosenPokemon should be(pikachu)
        updatedPokedex.showAvailablePokemon() should be("Bulbasaur")
      }

      "check if a Pokémon exists in the Pokedex" in {
        pokedex.exists("Pikachu") should be(true)
        pokedex.exists("Charmander") should be(false)
      }
    }

    "initialized without available Pokémon" should {
      val emptyPokedex = new Pokedex(Vector.empty)

      "show no available Pokémon" in {
        emptyPokedex.showAvailablePokemon() should be("")
      }

      "not choose any Pokémon" in {
        val (chosenPokemon, updatedPokedex) = emptyPokedex.choosePokemon("Pikachu")
        chosenPokemon should be(null)
        updatedPokedex.showAvailablePokemon() should be("")
      }

      "report that no Pokémon exist" in {
        emptyPokedex.exists("Pikachu") should be(false)
        emptyPokedex.exists("Charmander") should be(false)
      }
    }
  }
}
