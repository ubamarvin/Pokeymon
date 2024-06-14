package de.htwg.se.Pokeymon.Controller.ControllerComponent
import de.htwg.se.Pokeymon.Util.Observer.Observable
import de.htwg.se.Pokeymon.Util.Observer.Observer
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._
trait ControllerInterface extends Observable {
  def handleInput(input: String): Unit
  def undo: Unit
  def redo: Unit
  def printDisplay: String
  def getSceneContent: Content
}
