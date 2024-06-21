package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SetupSpec extends AnyWordSpec with Matchers {

  "The Setup object" when {

    "initialized" should {

      "contain predefined Moves" in {
        Setup.empty_move.name shouldEqual "empty"
        Setup.empty_move.power shouldEqual 0
        Setup.empty_move.moveType shouldEqual "normal"

        Setup.tackle.name shouldEqual "tackle"
        Setup.tackle.power shouldEqual 50
        Setup.tackle.moveType shouldEqual "normal"

        Setup.thunder.name shouldEqual "thunder"
        Setup.thunder.power shouldEqual 70
        Setup.thunder.moveType shouldEqual "elektro"

        Setup.bodyslam.name shouldEqual "bodyslam"
        Setup.bodyslam.power shouldEqual 30
        Setup.bodyslam.moveType shouldEqual "normal"

        Setup.kick.name shouldEqual "kick"
        Setup.kick.power shouldEqual 75
        Setup.kick.moveType shouldEqual "normal"

        Setup.waterJet.name shouldEqual "waterjet"
        Setup.waterJet.power shouldEqual 50
        Setup.waterJet.moveType shouldEqual "water"

        Setup.fireBreath.name shouldEqual "firebreath"
        Setup.fireBreath.power shouldEqual 50
        Setup.fireBreath.moveType shouldEqual "fire"

        Setup.vegankick.name shouldEqual "vegankick"
        Setup.vegankick.power shouldEqual 50
        Setup.vegankick.moveType shouldEqual "plant"

        Setup.burn.name shouldEqual "burn"
        Setup.burn.power shouldEqual 0
        Setup.burn.moveType shouldEqual "fire"
        Setup.burn.statusEffect shouldEqual "burn"
      }

      "contain predefined Pokemons" in {
        Setup.pikachu.name shouldEqual "pikachu"
        Setup.pikachu.hp shouldEqual 100
        Setup.pikachu.moves shouldEqual List(Setup.tackle, Setup.thunder)
        Setup.pikachu.speed shouldEqual 30
        Setup.pikachu.pokeType shouldEqual "elektro"

        Setup.firefox.name shouldEqual "firefox"
        Setup.firefox.hp shouldEqual 100
        Setup.firefox.moves shouldEqual List(Setup.fireBreath, Setup.burn)
        Setup.firefox.speed shouldEqual 100
        Setup.firefox.pokeType shouldEqual "fire"

        Setup.fish.name shouldEqual "fish"
        Setup.fish.hp shouldEqual 100
        Setup.fish.moves shouldEqual List(Setup.waterJet, Setup.vegankick)
        Setup.fish.speed shouldEqual 75
        Setup.fish.pokeType shouldEqual "water"

        Setup.ratmon.name shouldEqual "ratmon"
        Setup.ratmon.hp shouldEqual 100
        Setup.ratmon.moves shouldEqual List(Setup.bodyslam, Setup.kick)
        Setup.ratmon.speed shouldEqual 40
        Setup.ratmon.pokeType shouldEqual "normal"

        Setup.cowPokemon.name shouldEqual "cowpokemon"
        Setup.cowPokemon.hp shouldEqual 100
        Setup.cowPokemon.moves shouldEqual List(Setup.tackle, Setup.thunder)
        Setup.cowPokemon.speed shouldEqual 35
        Setup.cowPokemon.pokeType shouldEqual "normal"

        Setup.evoli.name shouldEqual "evoli"
        Setup.evoli.hp shouldEqual 100
        Setup.evoli.moves shouldEqual List(Setup.bodyslam, Setup.kick)
        Setup.evoli.speed shouldEqual 35
        Setup.evoli.pokeType shouldEqual "fight"
      }

      "contain a predefined opponent Trainer with Pokemon" in {
        Setup.opponent.pokemons should have size 1 // Update this with the correct size based on std_mons
        val opponentPokemon = Setup.opponent.pokemons.head
        opponentPokemon.name shouldEqual "waterPk"
        opponentPokemon.hp shouldEqual 100
        opponentPokemon.moves shouldEqual List(Setup.tackle)
        opponentPokemon.speed shouldEqual 50
        opponentPokemon.pokeType shouldEqual "water"
      }

      "contain a predefined Pokedex with available Pokemon" in {
        Setup.pokedex.available_pokemon should have size 6 // Update this with the correct size based on available_pokemon
        Setup.pokedex.showAvailablePokemon() shouldEqual "pikachu, ratmon, cowpokemon, evoli, fish, firefox"
        Setup.pokedex.exists("pikachu") shouldBe true
        Setup.pokedex.exists("charmander") shouldBe false
      }

      "contain a predefined Trainer with empty Vector of Pokemons" in {
        Setup.trainer_ash.pokemons should have size 0
      }
    }
  }
}
