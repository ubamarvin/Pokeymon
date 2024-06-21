package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StatusStrategySpec extends AnyWordSpec with Matchers {

  "A Pokemon with Status Strategies" when {

    val pikachu = Setup.pikachu.copy(status = new StatusEffectStrategyContext())

    "NormalState" should {
      "apply no effect and return unchanged Pokemon" in {
        val normalState = new NormalState
        val (updatedPokemon, msg) = normalState.applyEffect(pikachu)
        updatedPokemon shouldEqual pikachu
        msg shouldEqual ""
      }
    }

    "BurnedState" should {
      "apply burn effect and decrease HP" in {
        val burnedState = BurnedState(duration = 3, intensity = 5)
        val (updatedPokemon, msg) = burnedState.applyEffect(pikachu)
        updatedPokemon.hp shouldEqual 95 // Pikachu's HP - 5
        msg shouldEqual "-pikachu is hurt by burn, lost 5 HP\n"
      }

      "reduce duration correctly" in {
        val burnedState = BurnedState(duration = 3, intensity = 5)
        val reducedState = burnedState.reduceDuration(burnedState)
        reducedState.duration shouldEqual 2
      }

      "clear effect and return to NormalState" in {
        val burnedState = BurnedState(duration = 1, intensity = 5)
        val clearedPokemon = burnedState.clearEffect(pikachu)
        clearedPokemon.status.getStatusName() shouldEqual ""
      }
    }

    "PoisonedState" should {
      "apply poison effect and decrease HP" in {
        val poisonedState = PoisonedState(duration = 3, intensity = 5)
        val (updatedPokemon, msg) = poisonedState.applyEffect(pikachu)
        updatedPokemon.hp shouldEqual 95 // Pikachu's HP - 5
        msg shouldEqual "-pikachu is hurt by poison, lost 5 HP\n"
      }

      "reduce duration correctly" in {
        val poisonedState = PoisonedState(duration = 3, intensity = 5)
        val reducedState = poisonedState.reduceDuration(poisonedState)
        reducedState.duration shouldEqual 2
      }

      "clear effect and return to NormalState" in {
        val poisonedState = PoisonedState(duration = 1, intensity = 5)
        val clearedPokemon = poisonedState.clearEffect(pikachu)
        clearedPokemon.status.getStatusName() shouldEqual ""
      }
    }

    "SleepState" should {
      "apply sleep effect" in {
        val sleepState = SleepState(duration = 3)
        val (updatedPokemon, msg) = sleepState.applyEffect(pikachu)
        updatedPokemon.status.getStatusName() shouldEqual "sleep"
        msg shouldEqual "-pikachu is sleeping\n"
      }

      "reduce duration correctly" in {
        val sleepState = SleepState(duration = 3)
        val reducedState = sleepState.reduceDuration(sleepState)
        reducedState.duration shouldEqual 2
      }

      "clear effect and return to NormalState" in {
        val sleepState = SleepState(duration = 1)
        val clearedPokemon = sleepState.clearEffect(pikachu)
        clearedPokemon.status.getStatusName() shouldEqual ""
      }
    }

    "ParalyzedState" should {
      "apply paralyzed effect" in {
        val paralyzedState = ParalyzedState(duration = 3)
        val (updatedPokemon, msg) = paralyzedState.applyEffect(pikachu)
        updatedPokemon.status.getStatusName() shouldEqual "paralyzed"
        msg shouldEqual "-pikachu is paralyzed\n"
      }

      "reduce duration correctly" in {
        val paralyzedState = ParalyzedState(duration = 3)
        val reducedState = paralyzedState.reduceDuration(paralyzedState)
        reducedState.duration shouldEqual 2
      }

      "clear effect and return to NormalState" in {
        val paralyzedState = ParalyzedState(duration = 1)
        val clearedPokemon = paralyzedState.clearEffect(pikachu)
        clearedPokemon.status.getStatusName() shouldEqual ""
      }
    }
  }

  "StatusEffectStrategyContext" should {
    "set and get status correctly" in {
      val pikachu = Setup.pikachu.copy(status = new StatusEffectStrategyContext())
      val burnedState = BurnedState(duration = 3, intensity = 5)
      val updatedPikachu = pikachu.setStatus(burnedState)
      updatedPikachu.status.getStatusName() shouldEqual "burned"
      updatedPikachu.hp shouldEqual 95 // Pikachu's HP - 5
    }
  }
}
