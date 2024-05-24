package de.htwg.se.Pokeymon.Controller
import de.htwg.se.Pokeymon.Util.Observable
import de.htwg.se.Pokeymon.Model.Game

class Controller(var game: Game) extends Observable {

  // UserInputHandler for all states
  def handleInput(input: String): Unit =
    // game = game.handleInput
    game = game.handleInput(input)
    notifyObservers

  // StateSwitchers
  // only works when in MainState
  // if not in Main State just do nothing
  def switchPokemon(input: String): Unit =
    // Check if state is MainState
    // switch state to SwitchPokemonState
    // Model log must store switched info
    // if not just return
    // could type switch during pikpkstate
    notifyObservers

  def useItem(input: String): Unit =
    // Check if state is mainState
    // switch state to SwitchPokemonState
    // Model log must store switched info
    // if not just return
    // could type switch during pikpkstate
    notifyObservers

  def chooseMove(Input: String): Unit =
    // Chek if state is MainBattleSt
    // switch to choose move state
    notifyObservers

  def printDisplay(): String =
    game.gameToString()
}
