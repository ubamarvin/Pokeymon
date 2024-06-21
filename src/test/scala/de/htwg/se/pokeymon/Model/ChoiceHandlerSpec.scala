package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class ChoiceHandlerSpec extends AnyWordSpec {

  "A SwitchPokemonHandler" should {
    "handle both players switching their Pokémon" in {
      val player = Trainer("Ash", List(Pokemon("Pikachu", "electric", 100)))
      val opponent = Trainer("Gary", List(Pokemon("Blastoise", "water", 120)))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(null)
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon.name shouldEqual "Pikachu"
      result.opponent.currentPokemon.name shouldEqual "Blastoise"
    }

    "handle only the player switching their Pokémon" in {
      val player = Trainer("Ash", List(Pokemon("Pikachu", "electric", 100)))
      val opponent = Trainer("Gary", List(Pokemon("Blastoise", "water", 120)))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(null)
      val result = handler.handleChoice(playersChoice.copy(opponent = opponent.copy(choice = None)))

      result.player.currentPokemon.name shouldEqual "Pikachu"
      result.opponent.currentPokemon.name shouldEqual "Blastoise"
    }

    "handle only the opponent switching their Pokémon" in {
      val player = Trainer("Ash", List(Pokemon("Pikachu", "electric", 100)))
      val opponent = Trainer("Gary", List(Pokemon("Blastoise", "water", 120)))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(null)
      val result = handler.handleChoice(playersChoice.copy(player = player.copy(choice = None)))

      result.player.currentPokemon.name shouldEqual "Pikachu"
      result.opponent.currentPokemon.name shouldEqual "Blastoise"
    }

    "handle no Pokémon switching" in {
      val player = Trainer("Ash", List(Pokemon("Pikachu", "electric", 100)))
      val opponent = Trainer("Gary", List(Pokemon("Blastoise", "water", 120)))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = SwitchPokemonHandler(null)
      val result = handler.handleChoice(playersChoice.copy(player = player.copy(choice = None), opponent = opponent.copy(choice = None)))

      result.player.currentPokemon.name shouldEqual "Pikachu"
      result.opponent.currentPokemon.name shouldEqual "Blastoise"
    }
  }

  "An EvaluateAttackHandler" should {
    "handle both players attacking" in {
      val playerPokemon = Pokemon("Pikachu", "electric", 100)
      val opponentPokemon = Pokemon("Blastoise", "water", 120)
      val player = Trainer("Ash", List(playerPokemon))
      val opponent = Trainer("Gary", List(opponentPokemon))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(null)
      val result = handler.handleChoice(playersChoice.copy(
        player = player.copy(choice = Some(AttackChoice(Some(Move("Thunderbolt", "electric", 90, "none"))))),
        opponent = opponent.copy(choice = Some(AttackChoice(Some(Move("Surf", "water", 80, "none")))))
      ))

      result.player.currentPokemon.hp should be < playerPokemon.hp
      result.opponent.currentPokemon.hp should be < opponentPokemon.hp
    }

    "handle only the player attacking" in {
      val playerPokemon = Pokemon("Pikachu", "electric", 100)
      val opponentPokemon = Pokemon("Blastoise", "water", 120)
      val player = Trainer("Ash", List(playerPokemon))
      val opponent = Trainer("Gary", List(opponentPokemon))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(null)
      val result = handler.handleChoice(playersChoice.copy(
        player = player.copy(choice = Some(AttackChoice(Some(Move("Thunderbolt", "electric", 90, "none"))))),
        opponent = opponent.copy(choice = None)
      ))

      result.player.currentPokemon.hp should be < playerPokemon.hp
      result.opponent.currentPokemon.hp shouldEqual opponentPokemon.hp
    }

    "handle only the opponent attacking" in {
      val playerPokemon = Pokemon("Pikachu", "electric", 100)
      val opponentPokemon = Pokemon("Blastoise", "water", 120)
      val player = Trainer("Ash", List(playerPokemon))
      val opponent = Trainer("Gary", List(opponentPokemon))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(null)
      val result = handler.handleChoice(playersChoice.copy(
        player = player.copy(choice = None),
        opponent = opponent.copy(choice = Some(AttackChoice(Some(Move("Surf", "water", 80, "none")))))
      ))

      result.player.currentPokemon.hp shouldEqual playerPokemon.hp
      result.opponent.currentPokemon.hp should be < opponentPokemon.hp
    }

    "handle no attacks" in {
      val playerPokemon = Pokemon("Pikachu", "electric", 100)
      val opponentPokemon = Pokemon("Blastoise", "water", 120)
      val player = Trainer("Ash", List(playerPokemon))
      val opponent = Trainer("Gary", List(opponentPokemon))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = EvaluateAttackHandler(null)
      val result = handler.handleChoice(playersChoice.copy(
        player = player.copy(choice = None),
        opponent = opponent.copy(choice = None)
      ))

      result.player.currentPokemon.hp shouldEqual playerPokemon.hp
      result.opponent.currentPokemon.hp shouldEqual opponentPokemon.hp
    }
  }

  "A StatusHandler" should {
    "handle status effects for both players" in {
      val playerPokemon = Pokemon("Pikachu", "electric", 100, status = new BurnedState(3))
      val opponentPokemon = Pokemon("Blastoise", "water", 120, status = new PoisonedState(5))
      val player = Trainer("Ash", List(playerPokemon))
      val opponent = Trainer("Gary", List(opponentPokemon))
      val playersChoice = PlayersChoice(player, opponent, "")

      val handler = StatusHandler()
      val result = handler.handleChoice(playersChoice)

      result.player.currentPokemon.status.duration shouldEqual 2 // Burned status reduces by 1
      result.opponent.currentPokemon.status.duration shouldEqual 4 // Poisoned status reduces by 1
    }
  }
}
