package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StatusStrategySpec extends AnyWordSpec with Matchers {
  "A BurnedState" when {
    "applied to a Pokemon" should {
      "correctly decrease HP and reduce duration" in {
        // Arrange
        val burnedState = BurnedState(3)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, message) = burnedState.applyEffect(pokemon)

        // Assert
        updatedPokemon.hp should be < pokemon.hp
        message should startWith("-pikachu is hurt by burn")
        burnedState.duration shouldBe 2
      }

      "correctly clear the effect when duration is 0" in {
        // Arrange
        val burnedState = BurnedState(1)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, _) = burnedState.applyEffect(pokemon)
        val clearedPokemon = burnedState.clearEffect(updatedPokemon)

        // Assert
        clearedPokemon.status.strategy shouldBe a[NormalState]
      }
    }
  }

  "A PoisonedState" when {
    "applied to a Pokemon" should {
      "correctly decrease HP and reduce duration" in {
        // Arrange
        val poisonedState = PoisonedState(3)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, message) = poisonedState.applyEffect(pokemon)

        // Assert
        updatedPokemon.hp should be < pokemon.hp
        message should startWith("-pikachu is hurt by poison")
        poisonedState.duration shouldBe 2
      }

      "correctly clear the effect when duration is 0" in {
        // Arrange
        val poisonedState = PoisonedState(1)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, _) = poisonedState.applyEffect(pokemon)
        val clearedPokemon = poisonedState.clearEffect(updatedPokemon)

        // Assert
        clearedPokemon.status.strategy shouldBe a[NormalState]
      }
    }
  }

  "A SleepState" when {
    "applied to a Pokemon" should {
      "correctly reduce duration" in {
        // Arrange
        val sleepState = SleepState(3)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, message) = sleepState.applyEffect(pokemon)

        // Assert
        message should startWith("-pikachu is sleeping")
        sleepState.duration shouldBe 2
      }

      "correctly clear the effect when duration is 0" in {
        // Arrange
        val sleepState = SleepState(1)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, _) = sleepState.applyEffect(pokemon)
        val clearedPokemon = sleepState.clearEffect(updatedPokemon)

        // Assert
        clearedPokemon.status.strategy shouldBe a[NormalState]
      }
    }
  }

  "A ParalyzedState" when {
    "applied to a Pokemon" should {
      "correctly reduce duration" in {
        // Arrange
        val paralyzedState = ParalyzedState(3)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, message) = paralyzedState.applyEffect(pokemon)

        // Assert
        message should startWith("-pikachu is paralyzed")
        paralyzedState.duration shouldBe 2
      }

      "correctly clear the effect when duration is 0" in {
        // Arrange
        val paralyzedState = ParalyzedState(1)
        val pokemon = Setup.pikachu

        // Act
        val (updatedPokemon, _) = paralyzedState.applyEffect(pokemon)
        val clearedPokemon = paralyzedState.clearEffect(updatedPokemon)

        // Assert
        clearedPokemon.status.strategy shouldBe a[NormalState]
      }
    }
  }
}
