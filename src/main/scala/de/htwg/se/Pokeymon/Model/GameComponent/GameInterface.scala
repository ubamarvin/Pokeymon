package de.htwg.se.Pokeymon.Model.GameComponent
import de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._
trait GameInterface {
  def handleInput(input: String): Game
  def gameToString(): String
  def getContent(): Content
  def gameUndo(): Game
  def gameRedo(): Game
  def state: GameState
  def undoStack: Vector[GameState]
  def redoStack: Vector[GameState]
}
