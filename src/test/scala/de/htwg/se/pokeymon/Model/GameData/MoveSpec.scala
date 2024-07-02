package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MoveSpec extends AnyWordSpec with Matchers {

  "A Move" when {

    "created" should {

      "have correct properties" in {
        val move = Move("Thunderbolt", 90, "Electric")
        move.name should be("Thunderbolt")
        move.power should be(90)
        move.moveType should be("Electric")
        move.statusEffect should be("none")
      }

      "have a default status effect of 'none'" in {
        val move = Move("Thunderbolt", 90, "Electric")
        move.statusEffect should be("none")
      }

    }

    "converted to string" should {

      "produce the correct string representation" in {
        val move = Move("Thunderbolt", 90, "Electric", "Paralyze")
        move.moveToString() should be("Thunderbolt Power: 90 Type: Electric Effect: Paralyze")
      }

    }

  }

}
