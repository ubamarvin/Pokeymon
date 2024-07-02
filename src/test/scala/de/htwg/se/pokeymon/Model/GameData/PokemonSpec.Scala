package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PokemonSpec extends AnyWordSpec with Matchers {

  // Test data
  val move1 = Move("Thunderbolt", 90, "Electric")
  val move2 = Move("Fire Blast", 110, "Fire")
  val moves = List(move1, move2)
  val emptyMove = Move("Struggle", 50, "Normal") // Placeholder for non-existent move

  "A Pokemon" when {

    "created" should {

      "have correct initial properties" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")

        pokemon.id should be(1)
        pokemon.name should be("Pikachu")
        pokemon.hp should be(100)
        pokemon.moves should be(moves)
        pokemon.speed should be(90)
        pokemon.pokeType should be("Electric")
        pokemon.currentMove should be(emptyMove) // By default, currentMove should be emptyMove
        pokemon.canMove should be(true) // By default, canMove should be true
        pokemon.status.getStatusName() should be("")
      }

      "be alive when HP is greater than 0" in {
        val pokemon = Pokemon(1, "Pikachu", 50, moves, 90, "Electric")
        pokemon.isAlive() should be(true)
      }

      "be dead when HP is 0" in {
        val pokemon = Pokemon(1, "Pikachu", 0, moves, 90, "Electric")
        pokemon.isAlive() should be(false)
      }

    }

    "managing status" should {

      "correctly set and get status" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val burnedStatus = BurnedState(3)
        val updatedPokemon = pokemon.setStatus(burnedStatus)

        updatedPokemon.getStatus() should be("burned")
      }

    }

    "managing moves" should {

      "retrieve correct move power by name" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val power = pokemon.attack("Thunderbolt")
        power should be(Some(90))
      }

      "return None for non-existent move" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val power = pokemon.attack("Earthquake")
        power should be(None)
      }

      "set current move correctly" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val updatedPokemon = pokemon.setCurrentMove("Fire Blast")
        updatedPokemon.currentMove should be(move2)
      }

      "return moves as formatted string" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val movesString = pokemon.movesToString()
        movesString should include("Thunderbolt Power: 90 Type: Electric Effect: none")
        movesString should include("Fire Blast Power: 110 Type: Fire Effect: none")
      }

      "return list of moves" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val retrievedMoves = pokemon.getMoves()
        retrievedMoves should be(moves)
      }

    }

    "convert to string representation" should {

      "produce correct string representation" in {
        val pokemon = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
        val pokemonString = pokemon.toString
        pokemonString should include("Pikachu Hp: 100 Status: ")
      }

    }

    // Additional tests can be added for other methods and behaviors of Pokemon class

  }

}
