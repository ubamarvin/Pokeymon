package de.htwg.se.Pokeymon.Controller
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.Game
import de.htwg.se.Pokeymon.Model.CommandManager

class Controller(var game: Game) extends Observable {
  private val commandManager = new CommandManager

  // UserInputHandler for all states
  def handleInput(input: String): Unit =
    // cmd.exe forwards input into model.game and model.game
    // pushes playersChoice on cmdManager stack
    commandManager.doStep(new HandleInputCommand(input, this))
    // game = game.handleInput(input)
    notifyObservers

  def undo: Unit =
    // cmd manager "injects" game with top stacks playersChoice
    // puts current one on the fuckin redo stack
    println("undo in controller")
    commandManager.undoStep
    notifyObservers

  def redo: Unit =
    println("redo in controller")
    // ...
    commandManager.redoStep
    notifyObservers

  def printDisplay(): String =
    game.gameToString()
}
