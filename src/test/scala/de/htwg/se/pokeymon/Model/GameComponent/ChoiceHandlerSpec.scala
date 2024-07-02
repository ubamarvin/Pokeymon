package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChoiceHandlerSpec extends AnyWordSpec with Matchers {

  "A SwitchPokemonHandler" should {
    "handle both players switching their Pokémon" in {
      val switchPokemonHandler = SwitchPokemonHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = Some(SwitchPokemonChoice(Some(Setup.firefox))))
      val opponent = Setup.opponent.copy(choice = Some(SwitchPokemonChoice(Some(Setup.fish))))
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = switchPokemonHandler.handleChoice(playersChoice)

      updatedChoice.player.currentPokemon should be (Setup.firefox)
      updatedChoice.opponent.currentPokemon should be (Setup.fish)
      updatedChoice.roundReport should include ("-Player chooses firefox to fight!")
      updatedChoice.roundReport should include ("-Opponent chooses fish to fight!")
    }

    "handle only the player switching their Pokémon" in {
      val switchPokemonHandler = SwitchPokemonHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = Some(SwitchPokemonChoice(Some(Setup.firefox))))
      val opponent = Setup.opponent.copy(choice = None)
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = switchPokemonHandler.handleChoice(playersChoice)

      updatedChoice.player.currentPokemon should be (Setup.firefox)
      updatedChoice.roundReport should include ("-Player chooses firefox to fight!")
    }

    "handle only the opponent switching their Pokémon" in {
      val switchPokemonHandler = SwitchPokemonHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = None)
      val opponent = Setup.opponent.copy(choice = Some(SwitchPokemonChoice(Some(Setup.fish))))
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = switchPokemonHandler.handleChoice(playersChoice)

      updatedChoice.opponent.currentPokemon should be (Setup.fish)
      updatedChoice.roundReport should include ("-Opponent chooses fish to fight!")
    }
  }

  "An EvaluateAttackHandler" should {
    "handle both players attacking" in {
      val evaluateAttackHandler = EvaluateAttackHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = Some(AttackChoice(Some(Setup.thunder))))
      val opponent = Setup.opponent.copy(choice = Some(AttackChoice(Some(Setup.tackle))))
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = evaluateAttackHandler.handleChoice(playersChoice)

      // Verify the health points and the round report
      updatedChoice.player.currentPokemon.hp should be (70) // Assuming 30 damage from tackle
      updatedChoice.opponent.currentPokemon.hp should be (30) // Assuming 70 damage from thunder
      updatedChoice.roundReport should include ("thunder executed normally")
      updatedChoice.roundReport should include ("tackle executed normally")
    }

    "handle only the player attacking" in {
      val evaluateAttackHandler = EvaluateAttackHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = Some(AttackChoice(Some(Setup.thunder))))
      val opponent = Setup.opponent.copy(choice = None)
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = evaluateAttackHandler.handleChoice(playersChoice)

      // Verify the health points and the round report
      updatedChoice.opponent.currentPokemon.hp should be (30) // Assuming 70 damage from thunder
      updatedChoice.roundReport should include ("thunder executed normally")
    }

    "handle only the opponent attacking" in {
      val evaluateAttackHandler = EvaluateAttackHandler(StatusHandler())
      val player = Setup.trainer_ash.copy(choice = None)
      val opponent = Setup.opponent.copy(choice = Some(AttackChoice(Some(Setup.tackle))))
      val playersChoice = PlayersChoice(player, opponent, "")

      val updatedChoice = evaluateAttackHandler.handleChoice(playersChoice)

      // Verify the health points and the round report
      updatedChoice.player.currentPokemon.hp should be (70) // Assuming 30 damage from tackle
      updatedChoice.roundReport should include ("tackle executed normally")
    }
  }
}


