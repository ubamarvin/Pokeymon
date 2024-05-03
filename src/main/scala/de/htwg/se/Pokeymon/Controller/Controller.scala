package de.htwg.se.Pokeymon.Controller
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.Game

class Controller(var game: Game) extends Observable {

  def handleInput(input: String): Unit =
    // game = game.handleInput
    game = game.handleInput(input)
    notifyObservers

  def printDisplay(): String =
    game.gameToString()
}
