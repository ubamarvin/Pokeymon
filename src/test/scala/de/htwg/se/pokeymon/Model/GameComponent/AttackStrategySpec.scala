import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class AttackStrategyContextSpec extends AnyWordSpec with Matchers {

  "setContext" should {

    "set applyDamageStrategy for normal moves" in {
      val context = AttackStrategyContext()

      val move = Move("Tackle", 50, "normal")
      val updatedContext = context.setContext(move)

      updatedContext.attackStrategy shouldBe a [applyDamageStrategy]
    }

    "set applyStatusChangeStrategy for moves with status effects" in {
      val context = AttackStrategyContext()

      val move = Move("Burn", 0, "fire", "burn")
      val updatedContext = context.setContext(move)

      updatedContext.attackStrategy shouldBe a [applyStatusChangeStrategy]
    }

    "keep current strategy for moves with no status effect specified" in {
      val initialStrategy = new applyDamageStrategy()
      val context = AttackStrategyContext(initialStrategy)

      val move = Move("Tackle", 50, "normal")
      val updatedContext = context.setContext(move)

      updatedContext.attackStrategy shouldBe theSameInstanceAs (initialStrategy)
    }
"applyMove" should {

    "calculate damage for normal vs. fight type (not effective)" in {
      val strategy = new applyDamageStrategy()
      val attacker = Pokemon(1, "Attacker", 100, List(), 30, "normal")
      val defender = Pokemon(2, "Defender", 100, List(), 40, "fight")
      val move = Move("Tackle", 50, "normal")

      val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

      message shouldEqual "-Attacker's attack Tackle was not effective\n"
      updatedDefender.hp shouldEqual (defender.hp - (move.power * 0.5).toInt)
    }

    "calculate damage for fight vs. normal type (effective)" in {
      val strategy = new applyDamageStrategy()
      val attacker = Pokemon(1, "Attacker", 100, List(), 30, "fight")
      val defender = Pokemon(2, "Defender", 100, List(), 40, "normal")
      val move = Move("Punch", 50, "fight")

      val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

      message shouldEqual "-Attacker's attack Punch was effective\n"
      updatedDefender.hp shouldEqual (defender.hp - (move.power * 1.5).toInt)
    }

    "calculate damage for fire vs. water type (not effective)" in {
      val strategy = new applyDamageStrategy()
      val attacker = Pokemon(1, "Attacker", 100, List(), 30, "fire")
      val defender = Pokemon(2, "Defender", 100, List(), 40, "water")
      val move = Move("Fireball", 50, "fire")

      val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

      message shouldEqual "-Attacker's attack Fireball was not effective\n"
      updatedDefender.hp shouldEqual (defender.hp - (move.power * 0.5).toInt)
    }

    "calculate damage for plant vs. water type (effective)" in {
      val strategy = new applyDamageStrategy()
      val attacker = Pokemon(1, "Attacker", 100, List(), 30, "plant")
      val defender = Pokemon(2, "Defender", 100, List(), 40, "water")
      val move = Move("Vine Whip", 50, "plant")

      val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

      message shouldEqual "-Attacker's attack Vine Whip was effective\n"
      updatedDefender.hp shouldEqual (defender.hp - (move.power * 1.5).toInt)
    }

    "calculate damage for unknown type combination (normal effectiveness)" in {
      val strategy = new applyDamageStrategy()
      val attacker = Pokemon(1, "Attacker", 100, List(), 30, "unknown")
      val defender = Pokemon(2, "Defender", 100, List(), 40, "unknown")
      val move = Move("Unknown Move", 50, "unknown")

      val (updatedAttacker, updatedDefender, message) = strategy.applyMove(attacker, defender, move)

      message shouldEqual "-Attacker's attack Unknown Move was executed normally\n"
      updatedDefender.hp shouldEqual (defender.hp - move.power.toInt)
    }

  }
  }

}
