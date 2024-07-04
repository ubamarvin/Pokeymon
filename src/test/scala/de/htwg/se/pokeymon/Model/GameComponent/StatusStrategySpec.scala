package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class StatusStrategySpec extends AnyFlatSpec with Matchers {

  "BurnedState" should "apply burn effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Fire move", 50, "fire")), 50, "fire")
    val burnedState = BurnedState(3, 5)

    val (updatedPokemon, message) = burnedState.applyEffect(pokemon)

    updatedPokemon.hp shouldEqual 95 // decreased by intensity of burn
    message shouldEqual "-TestPokemon is hurt by burn, lost 5 HP\n"
    updatedPokemon.getStatus() shouldEqual "burned"
  }

  it should "clear burn effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Fire move", 50, "fire")), 50, "fire")
    val burnedState = BurnedState(1, 5)

    val updatedPokemon = burnedState.clearEffect(pokemon)

    updatedPokemon.getStatus() shouldEqual ""
  }

  "PoisonedState" should "apply poison effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Water move", 50, "water")), 50, "water")
    val poisonedState = PoisonedState(3, 5)

    val (updatedPokemon, message) = poisonedState.applyEffect(pokemon)

    updatedPokemon.hp shouldEqual 95 // decreased by intensity of poison
    message shouldEqual "-TestPokemon is hurt by poison, lost 5 HP\n"
    updatedPokemon.getStatus() shouldEqual "poisoned"
  }

  it should "clear poison effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Water move", 50, "water")), 50, "water")
    val poisonedState = PoisonedState(1, 5)

    val updatedPokemon = poisonedState.clearEffect(pokemon)

    updatedPokemon.getStatus() shouldEqual ""
  }

  "SleepState" should "apply sleep effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Normal move", 50, "normal")), 50, "normal")
    val sleepState = SleepState(2)

    val (updatedPokemon, message) = sleepState.applyEffect(pokemon)

    message shouldEqual "-TestPokemon is sleeping\n"
    updatedPokemon.getStatus() shouldEqual "sleep"
  }

  it should "clear sleep effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Normal move", 50, "normal")), 50, "normal")
    val sleepState = SleepState(1)

    val updatedPokemon = sleepState.clearEffect(pokemon)

    updatedPokemon.getStatus() shouldEqual ""
  }

  "ParalyzedState" should "apply paralysis effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Electric move", 50, "elektro")), 50, "elektro")
    val paralyzedState = ParalyzedState(2)

    val (updatedPokemon, message) = paralyzedState.applyEffect(pokemon)

    message shouldEqual "-TestPokemon is paralyzed\n"
    updatedPokemon.getStatus() shouldEqual "paralyzed"
  }

  it should "clear paralysis effect correctly" in {
    val pokemon = Pokemon(1, "TestPokemon", 100, List(Move("Electric move", 50, "elektro")), 50, "elektro")
    val paralyzedState = ParalyzedState(1)

    val updatedPokemon = paralyzedState.clearEffect(pokemon)

    updatedPokemon.getStatus() shouldEqual ""
  }

}
