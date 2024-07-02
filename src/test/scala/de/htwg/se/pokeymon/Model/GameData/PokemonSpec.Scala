package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PokemonSpec extends AnyWordSpec with Matchers {

  "A Pokemon" when {
    val tackle = Move("Tackle", 40, "normal")
    val thunderShock = Move("Thunder Shock", 50, "electric")
    val scratch = Move("Scratch", 35, "normal")
    val ember = Move("Ember", 40, "fire")

    val pikachu = Pokemon(1, "Pikachu", 100, List(tackle, thunderShock), 90, "electric")

    "initialized" should {
      "have correct initial values" in {
        pikachu.id should be(1)
        pikachu.name should be("Pikachu")
        pikachu.hp should be(100)
        pikachu.moves should contain theSameElementsAs List(tackle, thunderShock)
        pikachu.speed should be(90)
        pikachu.pokeType should be("electric")
        pikachu.currentMove should be(empty_move)
        pikachu.status.getStatusName() should be("None")
      }

      "be alive" in {
        pikachu.isAlive() should be(true)
      }

      "have a string representation" in {
        pikachu.toString() should be("Pikachu Hp: 100 Status: None")
      }

      "have string representation of moves" in {
        pikachu.movesToString() should be(
          s"""Tackle  Power: 40 Type: normal Effect: none
             |Thunder Shock  Power: 50 Type: electric Effect: none""".stripMargin
        )
      }
    }

    "attacked with a move" should {
      "return the power of the move if it exists" in {
        pikachu.attack("Tackle") should be(Some(40))
        pikachu.attack("Thunder Shock") should be(Some(50))
        pikachu.attack("Scratch") should be(None)
      }
    }

    "decreasing HP" should {
      "reduce HP correctly" in {
        val newPikachu = pikachu.decreaseHp(20)
        newPikachu.hp should be(80)
      }

      "remain alive if HP is greater than 0" in {
        val newPikachu = pikachu.decreaseHp(20)
        newPikachu.isAlive() should be(true)
      }

      "be dead if HP is 0 or less" in {
        val newPikachu = pikachu.decreaseHp(150)
        newPikachu.isAlive() should be(false)
      }
    }

    "changing status" should {
      "apply the new status correctly" in {
        val burnedPikachu = pikachu.setStatus(BurnedState(3))
        burnedPikachu.status.getStatusName() should be("Burned")
      }
    }

    "setting current move" should {
      "set the current move correctly" in {
        val pikachuWithMove = pikachu.setCurrentMove("Tackle")
        pikachuWithMove.currentMove should be(tackle)

        val pikachuWithUnknownMove = pikachu.setCurrentMove("Unknown Move")
        pikachuWithUnknownMove.currentMove should be(empty_move)
      }
    }
  }
}
