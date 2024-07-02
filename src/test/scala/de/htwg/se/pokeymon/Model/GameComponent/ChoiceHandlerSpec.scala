package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model.GameData._

class ChoiceHandlerSpec extends AnyWordSpec {

  "SwitchPokemonHandler" should {
    "handle both players switching their Pokémon" in {
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire")
      val newPlayerPokemon = Pokemon(3, "Bulbasaur", 100, List(), 30, "plant")
      val newOpponentPokemon = Pokemon(4, "Squirtle", 100, List(), 30, "water")
      val player = Trainer("Player", List(playerPokemon, newPlayerPokemon), Some(SwitchPokemonChoice(Some(newPlayerPokemon))), playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon, newOpponentPokemon), Some(SwitchPokemonChoice(Some(newOpponentPokemon))), opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon should be(newPlayerPokemon)
      result.opponent.currentPokemon should be(newOpponentPokemon)
      result.roundReport should include("Player chooses Bulbasaur to fight!")
      result.roundReport should include("Opponent chooses Squirtle to fight!")
    }

    "handle only the player switching Pokémon" in {
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire")
      val newPlayerPokemon = Pokemon(3, "Bulbasaur", 100, List(), 30, "plant")
      val player = Trainer("Player", List(playerPokemon, newPlayerPokemon), Some(SwitchPokemonChoice(Some(newPlayerPokemon))), playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), None, opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon should be(newPlayerPokemon)
      result.opponent.currentPokemon should be(opponentPokemon)
      result.roundReport should include("Player chooses Bulbasaur to fight!")
    }

    "handle only the opponent switching Pokémon" in {
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire")
      val newOpponentPokemon = Pokemon(3, "Squirtle", 100, List(), 30, "water")
      val player = Trainer("Player", List(playerPokemon), None, playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon, newOpponentPokemon), Some(SwitchPokemonChoice(Some(newOpponentPokemon))), opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon should be(playerPokemon)
      result.opponent.currentPokemon should be(newOpponentPokemon)
      result.roundReport should include("Opponent chooses Squirtle to fight!")
    }

    "pass the playersChoice to the next handler if no switch occurs" in {
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire")
      val player = Trainer("Player", List(playerPokemon), None, playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), None, opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result should be(playersChoice)
    }
  }

  "UseItemHandler" should {
    "do nothing if no items are available" in {
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire")
      val player = Trainer("Player", List(playerPokemon), None, playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), None, opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = UseItemHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result should be(playersChoice)
    }
  }

  "EvaluateAttackHandler" should {
    "handle both players attacking" in {
      val playerMove = Move("Thunder Shock", 40, "elektro")
      val opponentMove = Move("Ember", 40, "fire")
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(playerMove), 60, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(opponentMove), 50, "fire")
      val player = Trainer("Player", List(playerPokemon), Some(AttackChoice(Some(playerMove))), playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), Some(AttackChoice(Some(opponentMove))), opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.roundReport should include("Pikachu's attack Thunder Shock was executed normally")
      result.roundReport should include("Charmander's attack Ember was executed normally")
    }

    "handle only the player attacking" in {
      val playerMove = Move("Thunder Shock", 40, "elektro")
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(playerMove), 60, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 50, "fire")
      val player = Trainer("Player", List(playerPokemon), Some(AttackChoice(Some(playerMove))), playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), None, opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.roundReport should include("Pikachu's attack Thunder Shock was executed normally")
    }

    "handle only the opponent attacking" in {
      val opponentMove = Move("Ember", 40, "fire")
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 60, "elektro")
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(opponentMove), 50, "fire")
      val player = Trainer("Player", List(playerPokemon), None, playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), Some(AttackChoice(Some(opponentMove))), opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(StatusHandler())
      val result = handler.handleChoice(playersChoice)

      result.roundReport should include("Charmander's attack Ember was executed normally")
    }
  }

  "StatusHandler" should {
    "apply status effects to both players' Pokémon" in {
      val burnedState = new BurnedState(3)
      val playerPokemon = Pokemon(1, "Pikachu", 100, List(), 30, "elektro").setStatus(burnedState)
      val opponentPokemon = Pokemon(2, "Charmander", 100, List(), 30, "fire").setStatus(burnedState)
      val player = Trainer("Player", List(playerPokemon), None, playerPokemon)
      val opponent = Trainer("Opponent", List(opponentPokemon), None, opponentPokemon)
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = StatusHandler()
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon.hp should be < 100
      result.opponent.currentPokemon.hp should be < 100
      result.roundReport should include("Pikachu is hurt by its burn")
      result.roundReport should include("Charmander is hurt by its burn")
    }
  }
}
