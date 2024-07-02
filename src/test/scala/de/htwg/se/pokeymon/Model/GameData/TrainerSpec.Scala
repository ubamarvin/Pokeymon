package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TrainerSpec extends AnyWordSpec with Matchers {

  // Test data
  val move1 = Move("Thunderbolt", 90, "Electric")
  val move2 = Move("Fire Blast", 110, "Fire")
  val moves = List(move1, move2)
  val emptyMove = Move("Struggle", 50, "Normal") // Placeholder for non-existent move

  val pokemon1 = Pokemon(1, "Pikachu", 100, moves, 90, "Electric")
  val pokemon2 = Pokemon(2, "Charizard", 120, moves, 100, "Fire")
  val evoli = Pokemon(3, "Evoli", 80, moves, 80, "Normal")
  val trainer = Trainer(Vector(pokemon1, pokemon2), evoli)

  "A Trainer" when {

    "created" should {

      "have correct initial properties" in {
        trainer.pokemons should contain allOf (pokemon1, pokemon2)
        trainer.currentPokemon should be(evoli)
        trainer.choice should be(None)
      }

      "retrieve current and all pokemons" in {
        trainer.getPokemons() should contain allOf (pokemon1, pokemon2)
        trainer.getCurrentPokemon() should be(evoli)
      }

      "add a new pokemon" in {
        val newPokemon = Pokemon(4, "Blastoise", 110, moves, 85, "Water")
        val updatedTrainer = trainer.addPokemon(newPokemon)
        updatedTrainer.getPokemons() should contain(newPokemon)
      }

      "remove an existing pokemon by name" in {
        val pokemonNameToRemove = "Pikachu"
        val updatedTrainer = trainer.removePokemon(pokemonNameToRemove)
        updatedTrainer.getPokemons().map(_.name) should not contain pokemonNameToRemove
      }

      "switch the current pokemon with another" in {
        val newCurrentPokemon = Pokemon(5, "Bulbasaur", 95, moves, 70, "Grass")
        val updatedTrainer = trainer.switchPokemon(newCurrentPokemon)
        updatedTrainer.getCurrentPokemon() should be(newCurrentPokemon)
        updatedTrainer.getPokemons() should contain(trainer.currentPokemon)
      }

      "get next pokemon in list" in {
        val nextPokemon = trainer.getNextPokemon()
        nextPokemon should be(pokemon1)
      }

      "check if a pokemon exists by name" in {
        trainer.pokemonExists("Charizard") should be(true)
        trainer.pokemonExists("Squirtle") should be(false)
      }

      "get a pokemon by name" in {
        val pokemonByName = trainer.getPokemonByName("Charizard")
        pokemonByName should be(Some(pokemon2))
      }

      "check if trainer has no pokemon left" in {
        trainer.hasNoPokemonleft() should be(false)
        val defeatedTrainer = Trainer(Vector.empty, evoli)
        defeatedTrainer.hasNoPokemonleft() should be(true)
      }

    }

    // Additional tests can be added for other methods and behaviors of Trainer class

  }

}
