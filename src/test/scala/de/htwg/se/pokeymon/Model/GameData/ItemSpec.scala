import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.Model.GameData.Item

class ItemSpec extends AnyWordSpec with Matchers {

  "An Item" when {

    val item = Item("Potion")

    "created with a name" should {

      "have the correct name" in {
        item.name shouldEqual "Potion"
      }

    }

  }

}
