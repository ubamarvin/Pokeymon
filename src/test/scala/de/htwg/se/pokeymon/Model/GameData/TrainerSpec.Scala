package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TrainerSpec extends AnyWordSpec with Matchers {

  "A Trainer" should {
    "add and remove Pokémon correctly" in {
      val pikachu = Setup.pikachu
      val ratmon = Setup.ratmon
      val trainer = Trainer(Vector(pikachu, ratmon))

      val newPokemon = Setup.evoli
      val trainerWithAddedPokemon = trainer.addPokemon(newPokemon)
      trainerWithAddedPokemon.pokemons should contain(newPokemon)

      val trainerWithoutRatmon = trainer.removePokemon("ratmon")
      trainerWithoutRatmon.pokemons should not contain ratmon
    }

    "switch Pokémon correctly" in {
      val pikachu = Setup.pikachu
      val ratmon = Setup.ratmon
      val evoli = Setup.evoli
      val trainer = Trainer(Vector(pikachu, ratmon), evoli)

      val switchedTrainer = trainer.switchPokemon(ratmon)
      switchedTrainer.currentPokemon should be(ratmon)
      switchedTrainer.pokemons should contain(pikachu)
      switchedTrainer.pokemons should not contain evoli
    }

    "get Pokémon by name" in {
      val pikachu = Setup.pikachu
      val ratmon = Setup.ratmon
      val trainer = Trainer(Vector(pikachu, ratmon))

      val foundPikachu = trainer.getPokemonByName("pikachu")
      foundPikachu should be(Some(pikachu))

      val notFoundPokemon = trainer.getPokemonByName("charmander")
      notFoundPokemon should be(None)
    }

    "check if no Pokémon are left" in {
      val pikachu = Setup.pikachu
      val trainerWithPokemon = Trainer(Vector(pikachu))
      val trainerWithoutPokemon = Trainer(Vector())

      trainerWithPokemon.hasNoPokemonleft() should be(false)
      trainerWithoutPokemon.hasNoPokemonleft() should be(true)
    }
  }
}
