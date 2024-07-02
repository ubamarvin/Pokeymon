package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class SetupSpec extends AnyWordSpec {

  "The Setup object" should {

    "initialize moves correctly" in {
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

    "initialize Pokemons correctly" in {
      Setup.pikachu.id shouldEqual 1
      Setup.pikachu.name shouldEqual "pikachu"
      Setup.pikachu.hp shouldEqual 100
      Setup.pikachu.moves shouldEqual List(Setup.tackle, Setup.thunder)
      Setup.pikachu.speed shouldEqual 30
      Setup.pikachu.pokeType shouldEqual "elektro"

      Setup.firefox.id shouldEqual 12
      Setup.firefox.name shouldEqual "firefox"
      Setup.firefox.hp shouldEqual 100
      Setup.firefox.moves shouldEqual List(Setup.fireBreath, Setup.burn)
      Setup.firefox.speed shouldEqual 100
      Setup.firefox.pokeType shouldEqual "fire"

      Setup.fish.id shouldEqual 11
      Setup.fish.name shouldEqual "fish"
      Setup.fish.hp shouldEqual 100
      Setup.fish.moves shouldEqual List(Setup.waterJet, Setup.vegankick)
      Setup.fish.speed shouldEqual 75
      Setup.fish.pokeType shouldEqual "water"

      Setup.ratmon.id shouldEqual 2
      Setup.ratmon.name shouldEqual "ratmon"
      Setup.ratmon.hp shouldEqual 100
      Setup.ratmon.moves shouldEqual List(Setup.bodyslam, Setup.kick)
      Setup.ratmon.speed shouldEqual 40
      Setup.ratmon.pokeType shouldEqual "normal"

      Setup.cowPokemon.id shouldEqual 3
      Setup.cowPokemon.name shouldEqual "cowpokemon"
      Setup.cowPokemon.hp shouldEqual 100
      Setup.cowPokemon.moves shouldEqual List(Setup.tackle, Setup.thunder)
      Setup.cowPokemon.speed shouldEqual 35
      Setup.cowPokemon.pokeType shouldEqual "normal"

      Setup.evoli.id shouldEqual 4
      Setup.evoli.name shouldEqual "evoli"
      Setup.evoli.hp shouldEqual 100
      Setup.evoli.moves shouldEqual List(Setup.bodyslam, Setup.kick)
      Setup.evoli.speed shouldEqual 35
      Setup.evoli.pokeType shouldEqual "fight"
    }

    "initialize trainer and opponent correctly" in {
      Setup.trainer_ash.pokemons shouldEqual Vector.empty

      Setup.opponent.pokemons.size shouldEqual 1
      Setup.opponent.pokemons.headOption.foreach { pokemon =>
        pokemon.name match {
          case "waterPk" => pokemon.pokeType shouldEqual "water"
          case _ => fail("Unexpected opponent Pokemon type")
        }
      }
    }

    "initialize Pokedex correctly" in {
      val pokedex = Setup.pokedex
      pokedex.showAvailablePokemon() shouldEqual "pikachu, ratmon, cowpokemon, evoli, fish, firefox"
    }

  }

}
