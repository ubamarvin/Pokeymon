package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model.GameData._

class GameInterfaceSpec extends AnyWordSpec {

  "A GameInterface" should {
    
    "handle input correctly" in {
      val game = new GameImplementation()
      val input = "attack Thunder Shock"
      val newGame = game.handleInput(input)

      newGame should not be null
      newGame.currentState should not be game.currentState // Ensure the game state changes
    }

    "return a string representation of the game state" in {
      val game = new GameImplementation()
      val gameString = game.gameToString()

      gameString should include("Player:")
      gameString should include("Opponent:")
    }

    "get the current game content" in {
      val game = new GameImplementation()
      val content = game.getContent()

      content should not be null
      content.player should not be null
      content.opponent should not be null
    }

    "undo the last game action" in {
      val game = new GameImplementation()
      game.handleInput("attack Thunder Shock")
      val gameAfterUndo = game.gameUndo()

      gameAfterUndo should not be null
      gameAfterUndo.currentState should not be game.currentState // Ensure the game state changes
    }

    "redo the last undone game action" in {
      val game = new GameImplementation()
      game.handleInput("attack Thunder Shock")
      game.gameUndo()
      val gameAfterRedo = game.gameRedo()

      gameAfterRedo should not be null
      gameAfterRedo.currentState should not be game.currentState // Ensure the game state changes
    }
  }
}
