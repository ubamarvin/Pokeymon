package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Util.Command
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

class CommandManagerSpec extends AnyWordSpec with Matchers {

  // Mock commands for testing
  case class MockCommand(undoCalled: Boolean, redoCalled: Boolean) extends Command {
    override def executeStep(): Unit = {}
    override def undoStep(): Unit = {}
    override def redoStep(): Unit = {}
  }

  val mockCommand1 = MockCommand(false, false)
  val mockCommand2 = MockCommand(false, false)

  "A CommandManager" when {

    "initialized" should {

      "have empty undo and redo stacks" in {
        val manager = new CommandManager
        manager.undoStack should be(empty)
        manager.redoStack should be(empty)
      }

    }

    "doing a step" should {

      "execute the command and add it to undo stack" in {
        val manager = new CommandManager
        manager.doStep(mockCommand1)
        manager.undoStack should contain(mockCommand1)
        mockCommand1.undoCalled should be(false) // ensure executeStep() was called
      }

    }

    "undoing a step" should {

      "undo the last command and move it to redo stack" in {
        val manager = new CommandManager
        manager.doStep(mockCommand1)
        manager.doStep(mockCommand2)
        manager.undoStep
        mockCommand2.undoCalled should be(true)
        manager.undoStack should not contain (mockCommand2)
        manager.redoStack should contain(mockCommand2)
      }

      "handle undo when no commands are in undo stack" in {
        val manager = new CommandManager
        manager.undoStep // should not throw or change anything
        manager.undoStack should be(empty)
        manager.redoStack should be(empty)
      }

    }

    "redoing a step" should {

      "redo the last undone command and move it back to undo stack" in {
        val manager = new CommandManager
        manager.doStep(mockCommand1)
        manager.doStep(mockCommand2)
        manager.undoStep
        manager.redoStep
        mockCommand2.redoCalled should be(true)
        manager.redoStack should not contain (mockCommand2)
        manager.undoStack should contain(mockCommand2)
      }

      "handle redo when no commands are in redo stack" in {
        val manager = new CommandManager
        manager.redoStep // should not throw or change anything
        manager.undoStack should be(empty)
        manager.redoStack should be(empty)
      }

    }

  }

}
