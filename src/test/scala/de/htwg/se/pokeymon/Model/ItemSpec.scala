package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ItemSpec extends AnyWordSpec with Matchers {

  "An Item" when {
    "created" should {
      "have a name" in {
        val item = Item("Potion")
        item.name shouldEqual "Potion"
      }
    }
  }
  
  "Items" should {
    "be equal if they have the same name" in {
      val item1 = Item("Potion")
      val item2 = Item("Potion")
      item1 shouldEqual item2
    }
    
    "not be equal if they have different names" in {
      val item1 = Item("Potion")
      val item2 = Item("Berry")
      item1 should not equal item2
    }
  }
  
}
