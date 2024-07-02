package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec with Matchers {

  "The Game" when {
    "in PickPokemonState" should {
      "display the welcome message and available Pokemon" in {
        val player = Trainer(Vector())
        val pokedex = Setup.pokedex
        val opponent = Setup.opponent
        val gameState = PickPokemonState(player, pokedex, 0, opponent)

        gameState.gameToString() should include("Welcome to Pokemon BattleSimulator!")
        gameState.gameToString() should include("The available Pokemon are:")
      }

      "process input to add a Pokemon to the player's team" in {
        val player = Trainer(Vector())
        val pokedex = Setup.pokedex
        val opponent = Setup.opponent
        val gameState = PickPokemonState(player, pokedex, 0, opponent)
        val newState = gameState.processInput("pikachu")

        newState shouldBe a[PickPokemonState]
        newState.asInstanceOf[PickPokemonState].player.pokemons.map(_.name) should contain("pikachu")
      }

      "transition to MainState after picking enough Pokemon" in {
        val player = Trainer(Vector(Setup.pikachu))
        val pokedex = Setup.pokedex
        val opponent = Setup.opponent
        val gameState = PickPokemonState(player, pokedex, 5, opponent)
        val newState = gameState.processInput("firefox")

        newState shouldBe a[MainState]
      }
    }

    "in MainState" should {
      "display the main options" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = MainState(player, opponent)

        gameState.gameToString() should include("What will you do? : Attack, Item, Switch")
      }

      "transition to ChooseAttackState on 'attack' input" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = MainState(player, opponent)
        val newState = gameState.processInput("attack")

        newState shouldBe a[ChooseAttackState]
      }
    }

    "in ChooseAttackState" should {
      "display available moves" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = ChooseAttackState(player, opponent)

        gameState.gameToString() should include("tackle")
        gameState.gameToString() should include("thunder")
      }

      "process input to select a move" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = ChooseAttackState(player, opponent)
        val newState = gameState.processInput("thunder")

        newState shouldBe a[BattleEvalState]
      }
    }

    "in BattleEvalState" should {
      "evaluate the battle and transition back to MainState" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = BattleEvalState(player, opponent)
        val newState = gameState.processInput("")

        newState shouldBe a[MainState]
      }
    }

    "in ChooseItemState" should {
      "display available items" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = ChooseItemState(player, opponent)

        gameState.gameToString() should include("no items available")
      }

      "process input to use an item" in {
        val player = Trainer(Vector(Setup.pikachu)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = ChooseItemState(player, opponent)
        val newState = gameState.processInput("move")

        newState shouldBe a[BattleEvalState]
      }
    }

    "in SwitchPokemonState" should {
      "display available Pokemon to switch" in {
        val player = Trainer(Vector(Setup.pikachu, Setup.firefox)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = SwitchPokemonState(player, opponent)

        gameState.gameToString() should include("Choose:")
        gameState.gameToString() should include("firefox")
      }

      "process input to switch Pokemon" in {
        val player = Trainer(Vector(Setup.pikachu, Setup.firefox)).setCurrentPokemon(Setup.pikachu)
        val opponent = Setup.opponent.setCurrentPokemon(Setup.opponent.pokemons.head)
        val gameState = SwitchPokemonState(player, opponent)
        val newState = gameState.processInput("firefox")

        newState shouldBe a[BattleEvalState]
      }
    }
  }
}
