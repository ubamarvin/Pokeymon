import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.Model.GameData.Move

class MoveSpec extends AnyWordSpec with Matchers {

  "A Move" when {

    val move = Move("Tackle", 50, "normal")

    "created with default statusEffect" should {

      "have correct toString representation" in {
        move.moveToString() shouldEqual "Tackle Power: 50 Type: normal Effect: none"
      }

    }

    "created with a specific statusEffect" should {

      val moveWithEffect = Move("Fire Blast", 80, "fire", "burn")

      "have correct toString representation" in {
        moveWithEffect.moveToString() shouldEqual "Fire Blast Power: 80 Type: fire Effect: burn"
      }

    }

  }

}
