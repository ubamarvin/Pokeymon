package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ChoiceSpec extends AnyWordSpec {

  "An AttackChoice" should {
    "have the correct choiceType and contain the move" in {
      val move = Move("Tackle", "normal", 40, "none")
      val attackChoice = AttackChoice(Some(move))

      attackChoice.choiceType shouldEqual "attack"
      attackChoice.move shouldBe Some(move)
    }
  }

  "An ItemChoice" should {
    "have the correct choiceType and contain the item and targetPokemon" in {
      val item = Item("Potion", "Heals 20 HP")
      val pokemon = Pokemon("Charmander", "fire", 100)
      val itemChoice = ItemChoice(item, pokemon)
    }
  }

  "A SwitchPokemonChoice" should {
    "have the correct choiceType and contain the pokemon" in {
      val pokemon = Pokemon("Squirtle", "water", 80)
      val switchChoice = SwitchPokemonChoice(Some(pokemon))

      switchChoice.choiceType shouldEqual "switch"
      switchChoice.pokemon shouldBe Some(pokemon)
    }
  }
}
