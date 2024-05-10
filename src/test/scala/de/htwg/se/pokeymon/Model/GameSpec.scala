package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameStateSpec extends AnyWordSpec {

  "A PickPokemonState" should {
    "return a welcome message when gameToString() is called" in {
      val player = Trainer(Vector())
      val pokedex = new Pokedex()
      val state = PickPokemonState(player, pokedex, 0, Setup.opponent)
      val welcomeMsg = state.gameToString()
      welcomeMsg should include("Welcome to Pokemon BattleSimulator!")
    }

    "add a pokemon to the player's team when a valid pokemon name is input" in {
      val player = Trainer(Vector())
      val pokedex = new Pokedex()
      val state = PickPokemonState(player, pokedex, 0, Setup.opponent)
      val newState = state.processInput("Pikachu")
      // You can add assertions here to verify the new state after adding a pokemon
      // For example:
      // newState.player.pokemons should contain(Pokemon(...))
    }
  }

  "A BattleState" should {
    "display the player's and opponent's current pokemon when gameToString() is called" in {
      val player = Trainer(Vector(Pokemon(1, "Pikachu", 100, List(Move("tackle", 50), Move("thunder", 70)), 30)))
      val opponent = Trainer(Vector(Pokemon(2, "Charmander", 100, List(Move("tackle", 50), Move("flame", 70)), 30)))
      val state = BattleState(player, opponent)
      val battleMsg = state.gameToString()
      battleMsg should include("Pikachu")
      battleMsg should include("Charmander")
    }

    "switch to yourDeadState when either the player or opponent has no pokemon left" in {
      val player = Trainer(Vector())
      val opponent = Trainer(Vector(Pokemon(2, "Charmander", 100, List(Move("tackle", 50), Move("flame", 70)), 30)))
      val state = BattleState(player, opponent)
      val newState = state.processInput("Tackle") // Any move to trigger evaluation
      newState shouldBe a[yourDeadState]
    }
  }
}
