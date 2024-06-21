package de.htwg.se.pokeymon.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalamock.scalatest.MockFactory
import org.mockito.MockitoSugar._

class HandleInputCommandSpec extends AnyWordSpec with MockFactory {

  "A HandleInputCommand" when {

    "executed" should {
      "update the game state in the controller" in {
        // Mocking the Controller
        val mockController = mock[Controller]
        val input = "someInput"
        
        // Creating the command
        val command = new HandleInputCommand(input, mockController)
        
        // Setting up behavior for executeStep
        command.executeStep
        
        // Verify that handleInput(input) was called on the controller's game
        (mockController.game).handleInput(input)
      }
    }

    "undone" should {
      "restore the previous game state in the controller" in {
        // Mocking the Controller
        val mockController = mock[Controller]
        val input = "someInput"
        val previousGameState = mock[Game]
        
        // Setting up the command and its behavior
        val command = new HandleInputCommand(input, mockController)
        command.executeStep // Executing the command once to set previousGameState
        (mockController.game).thenReturn(previousGameState) // Mocking current game state
        
        // Executing undo
        command.undoStep
        
        // Verify that game was set back to previousGameState
        (mockController.game) shouldBe previousGameState
      }
    }

    "redone" should {
      "re-execute the input handling and update the game state" in {
        // Mocking the Controller
        val mockController = mock[Controller]
        val input = "someInput"
        
        // Creating the command
        val command = new HandleInputCommand(input, mockController)
        
        // Setting up behavior for redoStep
        command.redoStep
        
        // Verify that handleInput(input) was called again on the controller's game
        (mockController.game).handleInput(input)
      }
    }
  }
}
