package de.htwg.se.pokeymon.Model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.{Pokemon, Move, AttackStrategyContext, applyDamageStrategy, applyStatusChangeStrategy}

class AttackStrategySpec extends AnyFlatSpec with Matchers {

  // Corrected Pokemon instances with appropriate attributes
  val attacker = Pokemon(1, "Charizard", 100, List.empty, 100, "fire")
  val defender = Pokemon(2, "Blastoise", 120, List.empty, 80, "water")
  val move = Move("Flamethrower", "fire", 90, "none")

  "An AttackStrategy" should "apply damage correctly" in {
    val attackContext = AttackStrategyContext(new applyDamageStrategy())
    val (newAttacker, newDefender, msg) = attackContext.applyAttackStrategy(attacker, defender, move)

    // Corrected assertion using shouldBe from Matchers
    newAttacker shouldBe attacker
    newDefender.hp should be < defender.hp // Assuming hp is a field in Pokemon class
  }

  it should "apply status change correctly" in {
    val statusMove = Move("Solar Beam", "plant", 120, "paralyze")
    val attackContext = AttackStrategyContext(new applyStatusChangeStrategy())
    val (newAttacker, newDefender, msg) = attackContext.applyAttackStrategy(attacker, defender, statusMove)

    // Corrected assertion using shouldBe from Matchers
    newAttacker shouldBe attacker
    // Add appropriate checks for status change
  }
}
