package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class AttackStrategySpec extends AnyWordSpec {

  "An applyDamageStrategy" should {
    val attacker = Pokemon(1, "Attacker", 100, List(Move("Tackle", 50, "normal")), 30, "normal")
    val defender = Pokemon(2, "Defender", 100, List(Move("Tackle", 50, "normal")), 30, "fight")
    val move = Move("Tackle", 50, "normal")
    val damageStrategy = new applyDamageStrategy

    "apply normal damage" in {
      val (_, updatedDefender, message) = damageStrategy.applyMove(attacker, defender, move)
      updatedDefender.hp should be(75)
      message should include("effective")
    }

    "apply type effectiveness" in {
      val fireMove = Move("Flame Thrower", 50, "fire")
      val waterDefender = defender.copy(pokeType = "water")
      val (_, updatedDefender, message) = damageStrategy.applyMove(attacker, waterDefender, fireMove)
      updatedDefender.hp should be(75)
      message should include("not effective")
    }

    "apply super effectiveness" in {
      val waterMove = Move("Water Gun", 50, "water")
      val fireDefender = defender.copy(pokeType = "fire")
      val (_, updatedDefender, message) = damageStrategy.applyMove(attacker, fireDefender, waterMove)
      updatedDefender.hp should be(25)
      message should include("effective")
    }
  }

  "An applyStatusChangeStrategy" should {
    val attacker = Pokemon(1, "Attacker", 100, List(Move("Burn Attack", 50, "fire", "burn")), 30, "normal")
    val defender = Pokemon(2, "Defender", 100, List(Move("Tackle", 50, "normal")), 30, "normal")
    val burnMove = Move("Burn Attack", 50, "fire", "burn")
    val statusChangeStrategy = new applyStatusChangeStrategy

    "apply a burn status effect" in {
      val (_, updatedDefender, message) = statusChangeStrategy.applyMove(attacker, defender, burnMove)
      updatedDefender.status.getStatusName() should be("Burned")
      message should include("burned")
    }

    "handle unknown status effect" in {
      val unknownMove = Move("Unknown Attack", 50, "normal", "unknown")
      val (_, updatedDefender, message) = statusChangeStrategy.applyMove(attacker, defender, unknownMove)
      updatedDefender.status.getStatusName() should be("Healthy")
      message should include("had no Effect")
    }
  }

  "An AttackStrategyContext" should {
    val attacker = Pokemon(1, "Attacker", 100, List(Move("Tackle", 50, "normal")), 30, "normal")
    val defender = Pokemon(2, "Defender", 100, List(Move("Tackle", 50, "normal")), 30, "fight")
    val damageMove = Move("Tackle", 50, "normal")
    val burnMove = Move("Burn Attack", 50, "fire", "burn")
    var context = AttackStrategyContext()

    "apply the default damage strategy" in {
      val (_, updatedDefender, message) = context.applyAttackStrategy(attacker, defender, damageMove)
      updatedDefender.hp should be(75)
      message should include("effective")
    }

    "switch to status change strategy based on move" in {
      context = context.setContext(burnMove)
      val (_, updatedDefender, message) = context.applyAttackStrategy(attacker, defender, burnMove)
      updatedDefender.status.getStatusName() should be("Burned")
      message should include("burned")
    }
  }
}
