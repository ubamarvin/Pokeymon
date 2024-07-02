package de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplementation
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.CommandManager
import de.htwg.se.Pokeymon.Model.GameComponent.Content
import de.htwg.se.Pokeymon.PokeymonModule
import de.htwg.se.Pokeymon.Model.GameComponent.Game._

import com.google.inject.{Inject, Guice}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import com.google.inject.name.Named
import de.htwg.se.Pokeymon.Model.FileIo.FileIOInterface

class Controller @Inject() (var game: GameInterface) extends Observable with ControllerInterface {
  private val commandManager = new CommandManager
  val injector = Guice.createInjector(new PokeymonModule)
  val fileIo = injector.instance[FileIOInterface]

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
    // println("undo in controller")
    commandManager.undoStep
    notifyObservers

  def redo: Unit =
    // println("redo in controller")
    // ...
    commandManager.redoStep
    notifyObservers

  def save: Unit =
    fileIo.save(game)
    println("SaveGame \n")

  def load: Unit =
    game = fileIo.load
    println("loadgame \n")

  def printDisplay: String =
    game.gameToString()

  def getSceneContent: Content =
    game.getContent()

}
