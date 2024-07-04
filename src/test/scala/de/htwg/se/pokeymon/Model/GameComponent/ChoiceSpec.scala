package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChoiceSpec extends AnyWordSpec with Matchers {

  "An AttackChoice" should {
    "have choiceType 'attack'" in {
      val attackChoice = AttackChoice(None)
      attackChoice.choiceType shouldBe "attack"
    }

    "not contain a move when created with None" in {
      val attackChoice = AttackChoice(None)
      attackChoice.move shouldBe None
    }


  }


  "A SwitchPokemonChoice" should {
    "have choiceType 'switch'" in {
      val switchChoice = SwitchPokemonChoice(None)
      switchChoice.choiceType shouldBe "switch"
    }

    "not contain a pokemon when created with None" in {
      val switchChoice = SwitchPokemonChoice(None)
      switchChoice.pokemon shouldBe None
    }
  }
  
}
