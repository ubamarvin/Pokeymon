package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class GameInterfaceSpec extends AnyWordSpec {

  "A Game" should {

    "handle input correctly and change state" in {
      val initialState = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val game = Game(state = initialState)
      val input = "Pikachu"
      val newGame = game.handleInput(input)

      newGame should not be null
      newGame.state shouldBe a[PickPokemonState]
      newGame.state should not be initialState
    }

    "return a string representation of the game state" in {
      val game = Game(state = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent))
      val gameString = game.gameToString()

      gameString should include("Welcome to Pokemon BattleSimulator!")
      gameString should include("The available Pokemon are:")
    }

    "get the current game content" in {
      val game = Game(state = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent))
      val content = game.getContent()

      content should not be null
      content.player should not be null
      content.opponent should not be null
    }

    "undo the last game action" in {
      val initialState = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val game = Game(state = initialState).handleInput("Pikachu")
      val gameAfterUndo = game.gameUndo()

      gameAfterUndo should not be null
      gameAfterUndo.state shouldBe initialState
    }

    "redo the last undone game action" in {
      val initialState = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val game = Game(state = initialState).handleInput("Pikachu")
      val gameAfterUndo = game.gameUndo()
      val gameAfterRedo = gameAfterUndo.gameRedo()

      gameAfterRedo should not be null
      gameAfterRedo.state should not be initialState
    }
  }
}

class GameStateSpec extends AnyWordSpec {

  "A PickPokemonState" should {
    "process input and add Pokemon to the player's team" in {
      val state = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val newState = state.processInput("Pikachu")

      newState should not be state
      newState shouldBe a[PickPokemonState]
      newState.asInstanceOf[PickPokemonState].picks should be > 0
    }

    "return the correct content" in {
      val state = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val content = state.getContent()

      content.state should be("pick")
    }

    "return the correct string representation" in {
      val state = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("Welcome to Pokemon BattleSimulator!")
    }
  }

  "A MainState" should {
    "process input and transition to the correct state" in {
      val state = MainState(Trainer(Vector()), Setup.opponent)
      val newState = state.processInput("attack")

      newState should not be state
      newState shouldBe a[ChooseAttackState]
    }

    "return the correct content" in {
      val state = MainState(Trainer(Vector()), Setup.opponent)
      val content = state.getContent()

      content.state should be("main")
    }

    "return the correct string representation" in {
      val state = MainState(Trainer(Vector()), Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("What will you do? : Attack, Item, Switch")
    }
  }

  "A ChooseAttackState" should {
    "process input and transition to BattleEvalState" in {
      val state = ChooseAttackState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val newState = state.processInput("Thunder Shock")

      newState should not be state
      newState shouldBe a[BattleEvalState]
    }

    "return the correct content" in {
      val state = ChooseAttackState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val content = state.getContent()

      content.state should be("attack")
    }

    "return the correct string representation" in {
      val state = ChooseAttackState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("Opponents Pokemon:")
    }
  }

  "A ChooseItemState" should {
    "process input and transition to MainState" in {
      val state = ChooseItemState(Trainer(Vector()), Setup.opponent)
      val newState = state.processInput("back")

      newState should not be state
      newState shouldBe a[MainState]
    }

    "return the correct content" in {
      val state = ChooseItemState(Trainer(Vector()), Setup.opponent)
      val content = state.getContent()

      content.state should be("item")
    }

    "return the correct string representation" in {
      val state = ChooseItemState(Trainer(Vector()), Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("Choose Item:")
    }
  }

  "A SwitchPokemonState" should {
    "process input and transition to BattleEvalState" in {
      val state = SwitchPokemonState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val newState = state.processInput("Pikachu")

      newState should not be state
      newState shouldBe a[BattleEvalState]
    }

    "return the correct content" in {
      val state = SwitchPokemonState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val content = state.getContent()

      content.state should be("switch")
    }

    "return the correct string representation" in {
      val state = SwitchPokemonState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("Choose:")
    }
  }

  "A BattleEvalState" should {
    "process input and transition to MainState or YourDeadState" in {
      val state = BattleEvalState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val newState = state.processInput("tackle")

      newState should not be state
      newState should (be a[MainState] or be a[YourDeadState])
    }

    "return the correct content" in {
      val state = BattleEvalState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val content = state.getContent()

      content.state should be("battle")
    }

    "return the correct string representation" in {
      val state = BattleEvalState(Trainer(Vector(Setup.pokedex.choosePokemon("Pikachu")._1)), Setup.opponent)
      val gameString = state.gameToString()

      gameString should include("are you sure?")
    }
  }

  "A YourDeadState" should {
    "process input and restart the game or remain in the state" in {
      val state = YourDeadState(Trainer(Vector()), Setup.opponent, "You lost!")
      val newState = state.processInput("ja")

      newState should not be state
      newState shouldBe a[PickPokemonState]
    }

    "return the correct content" in {
      val state = YourDeadState(Trainer(Vector()), Setup.opponent, "You lost!")
      val content = state.getContent()

      content.state should be("dead")
    }

    "return the correct string representation" in {
      val state = YourDeadState(Trainer(Vector()), Setup.opponent, "You lost!")
      val gameString = state.gameToString()

      gameString should include("won this Round!!!!")
    }
  }
}
