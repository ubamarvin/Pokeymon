package de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplementation

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Model.GameComponent.{GameInterface, Content}
import de.htwg.se.Pokeymon.Model.CommandManager

class ControllerSpec extends AnyWordSpec with MockitoSugar {

  "A Controller" should {
    val gameMock = mock[GameInterface]
    val commandManagerMock = mock[CommandManager]
    val controller = new Controller(gameMock) {
      override private val commandManager: CommandManager = commandManagerMock
    }

    "handle user input correctly" in {
      val input = "some input"
      controller.handleInput(input)
      verify(commandManagerMock).doStep(any[HandleInputCommand])
      verify(controller).notifyObservers()
    }

    "undo the last command" in {
      controller.undo
      verify(commandManagerMock).undoStep
      verify(controller).notifyObservers()
    }

    "redo the last undone command" in {
      controller.redo
      verify(commandManagerMock).redoStep
      verify(controller).notifyObservers()
    }

    "return the game display as a string" in {
      val display = "game state display"
      when(gameMock.gameToString()).thenReturn(display)
      controller.printDisplay should be(display)
    }

    "return the current scene content" in {
      val content = mock[Content]
      when(gameMock.getContent()).thenReturn(content)
      controller.getSceneContent should be(content)
    }
  }
}
