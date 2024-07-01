package de.htwg.se.Pokeymon.Controller.ControllerComponent
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._
import de.htwg.se.Pokeymon.Model.GameComponent.Content
import java.util.concurrent.TimeUnit

trait ControllerInterface extends Observable {
  def handleInput(input: String): Unit
  def undo: Unit
  def redo: Unit
  def save: Unit
  def load: Unit
  def printDisplay: String
  def getSceneContent: Content
}
