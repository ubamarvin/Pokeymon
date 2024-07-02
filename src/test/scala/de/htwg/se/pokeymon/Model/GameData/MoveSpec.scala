package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MoveSpec extends AnyWordSpec with Matchers {

  "A Move" should {
    "be created with a name, power, and type" in {
      val tackle = Move("Tackle", 40, "normal")
      tackle.name should be("Tackle")
      tackle.power should be(40)
      tackle.moveType should be("normal")
      tackle.statusEffect should be("none")
    }

    "be created with an optional status effect" in {
      val thunderWave = Move("Thunder Wave", 0, "electric", "paralyze")
      thunderWave.name should be("Thunder Wave")
      thunderWave.power should be(0)
      thunderWave.moveType should be("electric")
      thunderWave.statusEffect should be("paralyze")
    }

    "have a correct string representation" in {
      val flamethrower = Move("Flamethrower", 90, "fire")
      flamethrower.moveToString() should be("Flamethrower  Power: 90 Type: fire Effect: none")

      val toxic = Move("Toxic", 0, "poison", "poison")
      toxic.moveToString() should be("Toxic  Power: 0 Type: poison Effect: poison")
    }
  }
}
