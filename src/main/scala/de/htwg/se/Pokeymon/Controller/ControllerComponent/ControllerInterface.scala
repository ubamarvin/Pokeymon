package de.htwg.se.Pokeymon.Controller.ControllerComponent
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

import java.util.concurrent.TimeUnit
import play.api.libs.json._

trait ControllerInterface extends Observable {
  def handleInput(input: String): Unit
  def undo: Unit
  def redo: Unit
  def save: Unit
  def load: Unit
  def printDisplay: String
  
  def getGameJson: JsValue

}
