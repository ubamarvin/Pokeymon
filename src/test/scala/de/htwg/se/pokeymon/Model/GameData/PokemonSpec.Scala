package de.htwg.se.Pokeymon.Model.GameData

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData.Setup._

class PokemonSpec extends AnyFlatSpec with Matchers {

  "A Pokemon" should "decrease HP correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    val updatedPikachu = pikachu.decreaseHp(20)
    updatedPikachu.hp shouldEqual 80
  }

  it should "check if it is alive" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.isAlive() shouldBe true
    val faintedPikachu = pikachu.copy(hp = 0)
    faintedPikachu.isAlive() shouldBe false
  }

  it should "set and get current move correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    val updatedPikachu = pikachu.setCurrentMove("thunder")
    updatedPikachu.currentMove.name shouldEqual "thunder"
  }

  it should "set and get status correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    val burnedPikachu = pikachu.setStatus(new BurnedState(3))
    burnedPikachu.getStatus() shouldEqual "Burned"
  }
"A Pokemon" should "correctly check if it is alive" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.isAlive() shouldBe true
    val faintedPikachu = pikachu.copy(hp = 0)
    faintedPikachu.isAlive() shouldBe false
  }

  it should "return the power of a move by name" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.attack("thunder") shouldEqual Some(40)
    pikachu.attack("nonexistentMove") shouldEqual None
  }

  it should "set and get status correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    val burnedPikachu = pikachu.setStatus(new BurnedState(3))
    burnedPikachu.getStatus() shouldEqual "Burned"
  }

  it should "set and get current move correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    val updatedPikachu = pikachu.setCurrentMove("thunder")
    updatedPikachu.currentMove.name shouldEqual "thunder"
  }

  it should "return moves as a formatted string" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.movesToString() shouldEqual "Tackle Power: 40 Type: normal Effect: none\nThunder Power: 40 Type: elektro Effect: none"
  }

  it should "return its HP value" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.getHp() shouldEqual 100
  }

  it should "return a string representation of the Pokemon" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(tackle, thunder), 30, "elektro")
    pikachu.toString shouldEqual "pikachu Hp: 100 Status: Healthy"
  }

}
