package de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplementation
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.CommandManager
import de.htwg.se.Pokeymon.Model.GameComponent.Content

class Controller @Inject() (var game: GameInterface) extends Observable with ControllerInterface {
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

  def printDisplay: String =
    game.gameToString()

  def getSceneContent: Content =
    game.getContent()

}
