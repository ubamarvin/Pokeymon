package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MoveSpec extends AnyWordSpec {

  "A Move" when {
    "initialized with a name and power" should {
      val move = Move("Thunderbolt", 90)

      "have the correct name" in {
        assert(move.name == "Thunderbolt")
      }

      "have the correct power" in {
        assert(move.power == 90)
      }
    }
  }
}
