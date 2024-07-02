package de.htwg.se.Pokeymon.Model
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
class ItemSpec extends AnyWordSpec with Matchers {

  "An Item" should {
    "be created with a name" in {
      val potion = Item("Potion")
      potion.name should be("Potion")
    }

    "have a name that can be accessed" in {
      val superPotion = Item("Super Potion")
      superPotion.name should be("Super Potion")
    }

    "be correctly comparable with another item" in {
      val item1 = Item("Pokeball")
      val item2 = Item("Pokeball")
      val item3 = Item("Great Ball")

      item1 should be(item2)
      item1 should not be item3
    }
  }
}


