package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class AttackStrategySpec extends AnyWordSpec with Matchers {
  "An applyDamageStrategy" when {
    "applied to a move" should {
      "correctly calculate damage based on move and defender type" in {
        // Arrange
        val strategy = new applyDamageStrategy()
        val attacker = Setup.pikachu
        val defender = Setup.testmon4 // Assuming it's a plant type
        val move = Setup.tackle

        // Act
        val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)
        // Assert
        updatedAttacker shouldBe attacker
        updatedDefender.hp should be < defender.hp
        message should startWith("-pikachus attack tackle was ")
      }

      "handle special cases like type effectiveness" in {
        // Arrange
        val strategy = new applyDamageStrategy()
        val attacker = Setup.pikachu
        val defender = Setup.testmon2 // Assuming it's a fire type
        val move = Setup.thunder

        // Act
        val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

        // Assert
        updatedAttacker shouldBe attacker
        updatedDefender.hp should be < defender.hp
        message should startWith("-pikachus attack thunder was effective")
      }
    }
  }

  "An applyStatusChangeStrategy" when {
    "applied to a move" should {
      "correctly apply status changes based on the move" in {
        // Arrange
        val strategy = new applyStatusChangeStrategy()
        val attacker = Setup.ratmon
        val defender = Setup.testmon1 // Assuming it's a water type
        val move = Setup.burn

        // Act
        val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

        // Assert
        updatedAttacker shouldBe attacker
        updatedDefender.status should not be "Healthy"
        message should startWith("-waterPks attack hadno Effect")
      }
    }
  }

  "An AttackStrategyContext" when {
    "using applyAttackStrategy" should {
      "correctly apply the selected attack strategy" in {
        // Arrange
        val context = AttackStrategyContext()
        val attacker = Setup.firefox
        val defender = Setup.testmon2 // Assuming it's a fire type
        val move = Setup.fireBreath

        // Act
        val (updatedAttacker, updatedDefender, message) = context.applyAttackStrategy(attacker, defender, move)

        // Assert
        updatedAttacker shouldBe attacker
        updatedDefender.hp should be < defender.hp
        message should startWith("-firefoxs attack firebreath was ")
      }
    }

    "using setContext" should {
      "correctly switch the attack strategy based on move type" in {
        // Arrange
        val context = AttackStrategyContext()
        val moveWithStatus = Setup.burn

        // Act
        val newContext = context.setContext(moveWithStatus)

        // Assert
        newContext.attackStrategy shouldBe a[applyStatusChangeStrategy]
      }
    }
  }
}
