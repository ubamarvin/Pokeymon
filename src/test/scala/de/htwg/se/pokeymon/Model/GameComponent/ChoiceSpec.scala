package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChoiceSpec extends AnyWordSpec with Matchers {

  "An AttackChoice" should {
    "have a choiceType of 'attack'" in {
      val move = Some(Setup.thunder) // Verwende den Move aus der Setup-Klasse
      val choice = AttackChoice(move)
      
      choice.choiceType should be("attack")
      choice.move should be(move)
    }

    "handle None as a move" in {
      val choice = AttackChoice(None)
      
      choice.choiceType should be("attack")
      choice.move should be(None)
    }
  }

  "An ItemChoice" should {
    "have a choiceType of 'item'" in {
      val item = Item("Potion") // Ein einfaches Item erstellen
      val pokemon = Setup.pikachu // Verwende das Pokémon aus der Setup-Klasse
      val choice = ItemChoice(item, pokemon)
      
      choice.choiceType should be("item")
      choice.item should be(item)
      choice.targetPokemon should be(pokemon)
    }
  }

  "A SwitchPokemonChoice" should {
    "have a choiceType of 'switch'" in {
      val pokemon = Some(Setup.firefox) // Verwende das Pokémon aus der Setup-Klasse
      val choice = SwitchPokemonChoice(pokemon)
      
      choice.choiceType should be("switch")
      choice.pokemon should be(pokemon)
    }

    "handle None as a pokemon" in {
      val choice = SwitchPokemonChoice(None)
      
      choice.choiceType should be("switch")
      choice.pokemon should be(None)
    }
  }
}
