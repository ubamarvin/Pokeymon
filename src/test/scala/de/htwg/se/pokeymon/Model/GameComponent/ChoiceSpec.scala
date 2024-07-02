package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class ChoiceSpec extends AnyWordSpec {

  "An AttackChoice" should {
    "return the correct choiceType" in {
      val move = Move("Tackle", 50, "normal")
      val attackChoice = AttackChoice(Some(move))
      attackChoice.choiceType should be("attack")
    }

    "handle None as move" in {
      val attackChoice = AttackChoice(None)
      attackChoice.choiceType should be("attack")
    }
  }

  "An ItemChoice" should {
    "return the correct choiceType" in {
      val item = Item("Potion", "heal", 20)
      val pokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val itemChoice = ItemChoice(item, pokemon)
      itemChoice.choiceType should be("item")
    }

    "contain the correct item and targetPokemon" in {
      val item = Item("Potion", "heal", 20)
      val pokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val itemChoice = ItemChoice(item, pokemon)
      itemChoice.item should be(item)
      itemChoice.targetPokemon should be(pokemon)
    }
  }

  "A SwitchPokemonChoice" should {
    "return the correct choiceType" in {
      val pokemon = Pokemon(2, "Bulbasaur", 100, List(), 30, "plant")
      val switchChoice = SwitchPokemonChoice(Some(pokemon))
      switchChoice.choiceType should be("switch")
    }

    "handle None as pokemon" in {
      val switchChoice = SwitchPokemonChoice(None)
      switchChoice.choiceType should be("switch")
    }
  }
}
