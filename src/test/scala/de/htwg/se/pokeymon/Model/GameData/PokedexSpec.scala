package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PokedexSpec extends AnyWordSpec {

  // Test data
  val pokemon1 = Pokemon("Pikachu", 25, "Electric")
  val pokemon2 = Pokemon("Bulbasaur", 1, "Grass")

  "A Pokedex" when {
    "created with initial Pokemon" should {
      "return the list of available Pokemon names" in {
        val pokedex = new Pokedex(Vector(pokemon1, pokemon2))
        pokedex.showAvailablePokemon() shouldEqual "Pikachu, Bulbasaur"
      }
    }

    "checking if a Pokemon exists" should {
      "return true if the Pokemon exists in the Pokedex" in {
        val pokedex = new Pokedex(Vector(pokemon1, pokemon2))
        pokedex.exists("Pikachu") shouldEqual true
      }

      "return false if the Pokemon does not exist in the Pokedex" in {
        val pokedex = new Pokedex(Vector(pokemon1, pokemon2))
        pokedex.exists("Charmander") shouldEqual false
      }
    }

    "choosing a Pokemon by name" should {
      "return the chosen Pokemon and update the Pokedex" in {
        val pokedex = new Pokedex(Vector(pokemon1, pokemon2))
        val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("Pikachu")

        chosenPokemon shouldEqual pokemon1
        updatedPokedex.showAvailablePokemon() shouldEqual "Bulbasaur"
      }

      "return the remaining Pokedex unchanged if Pokemon name not found" in {
        val pokedex = new Pokedex(Vector(pokemon1, pokemon2))
        val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("Charmander")

        chosenPokemon shouldEqual null // or handle accordingly based on your implementation
        updatedPokedex.showAvailablePokemon() shouldEqual "Pikachu, Bulbasaur"
      }
    }
  }

}
