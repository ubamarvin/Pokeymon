package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData.Setup._

class SetupSpec extends AnyFlatSpec with Matchers {

  "Setup" should "initialize basic moves correctly" in {
    tackle.name shouldEqual "tackle"
    tackle.power shouldEqual 50
    tackle.moveType shouldEqual "normal"

    thunder.name shouldEqual "thunder"
    thunder.power shouldEqual 70
    thunder.moveType shouldEqual "elektro"
  }

  it should "initialize basic pokemons correctly" in {
    pikachu.name shouldEqual "pikachu"
    pikachu.hp shouldEqual 100
    pikachu.moves shouldEqual pikachu_moves
    pikachu.speed shouldEqual 30
    pikachu.pokeType shouldEqual "elektro"

    firefox.name shouldEqual "firefox"
    firefox.hp shouldEqual 100
    firefox.moves shouldEqual fire_moves
    firefox.speed shouldEqual 100
    firefox.pokeType shouldEqual "fire"
  }

  it should "initialize a trainer correctly" in {
    trainer_ash.getPokemons() shouldBe empty
    trainer_ash.getCurrentPokemon() shouldEqual evoli
  }

  it should "initialize an opponent correctly" in {
    opponent.getPokemons().size shouldEqual 1
    opponent.getPokemons().head.name shouldEqual "waterPk"
  }

  it should "initialize the pokedex correctly" in {
    pokedex.showAvailablePokemon() shouldEqual "pikachu, ratmon, cowpokemon, evoli, fish, firefox"
    pokedex.exists("pikachu") shouldBe true
    pokedex.exists("nonExistentPokemon") shouldBe false
  }

}
