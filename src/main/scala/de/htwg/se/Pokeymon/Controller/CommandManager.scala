package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Util.Command
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

//command needs to be playersChoice
class CommandManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil
  def doStep(command: Command) = {
    undoStack = command :: undoStack // pop command on top of stack
    command.executeStep // as its writtn
  }
  def undoStep = {
    // println("undo in manager")
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }
  def redoStep = {
    // println("redo in manager")
    redoStack match {
      case Nil =>
      case head :: stack => {
        head.redoStep
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }
}