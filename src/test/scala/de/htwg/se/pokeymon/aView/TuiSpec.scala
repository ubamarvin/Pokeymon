package de.htwg.se.Pokeymon.aView

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Controller.Controller
import de.htwg.se.Pokeymon.Model.Game

class TuiSpec extends AnyWordSpec with Matchers {
  "The Tui" when {
    "processing input" should {
      "handle 'q' as quit command" in {
        // Arrange
        val game = new Game()
        val controller = new Controller(game)
        val tui = new Tui(controller)

        // Act
        val input = "q"
        tui.processInput(input)

        // Assert
        // Verify that the Tui responds appropriately to the quit command
      }

      "handle other inputs by passing them to the controller" in {
        // Arrange
        val game = new Game()
        val controller = new Controller(game)
        val tui = new Tui(controller)

        // Act
        val input = "some_input"
        tui.processInput(input)

        // Assert
        // Verify that the Tui passes other inputs to the controller
      }
    }

    "updating" should {
      "display the current game state" in {
        // Arrange
        val game = new Game()
        val controller = new Controller(game)
        val tui = new Tui(controller)

        // Act
        // Simulate an update from the observable
        tui.update

        // Assert
        // Verify that the current game state is displayed
      }
    }
  }
}
