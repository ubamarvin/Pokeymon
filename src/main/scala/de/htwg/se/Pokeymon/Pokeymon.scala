package de.htwg.se.Pokeymon

import de.htwg.se.Pokeymon.Controller.Controller
import de.htwg.se.Pokeymon.aView.Tui
import de.htwg.se.Pokeymon.Model.Game

object pokeymon {
  val game = new Game()
  val controller = new Controller(game) // why new?
  val tui = new Tui(controller)
  controller.notifyObservers // Warum

  @main
  def startGame(): Unit =
    println("Welcome to Pokeymon")
    var input: String = ""

    while (input != "q") {
      input = scala.io.StdIn.readLine()
      tui.processInput(input)
    }

}
