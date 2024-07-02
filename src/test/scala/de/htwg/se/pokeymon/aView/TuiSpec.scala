package de.htwg.se.Pokeymon.aView

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface

class TuiSpec extends AnyWordSpec with MockitoSugar {
  
  "A Tui" should {
    val controller = mock[ControllerInterface]
    val tui = new Tui(controller)

    "quit the game when input is 'q'" in {
      val input = "q"
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInput(input)
      }
      stream.toString should include("Game quitted")
    }

    "undo the last command when input is 'z'" in {
      val input = "z"
      tui.processInput(input)
      verify(controller).undo
    }

    "redo the last undone command when input is 'y'" in {
      val input = "y"
      tui.processInput(input)
      verify(controller).redo
    }

    "handle other input correctly" in {
      val input = "any other input"
      tui.processInput(input)
      verify(controller).handleInput(input)
    }

    "print the display when update is called" in {
      when(controller.printDisplay).thenReturn("game state")
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.update
      }
      stream.toString should include("game state")
    }
  }
}
