package de.htwg.se.Pokeymon.aView

import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface

class Tui(controller: ControllerInterface) extends Observer {

  controller.add(this)

  def processInput(input: String): Unit = {

    input match {
      case "q" =>
        println("Game quitted")
      case "z" => controller.undo
      case "y" => controller.redo
      case _   => controller.handleInput(input)
    }
  }
  // update must also communicate game state
  // das die vom observable gerufende update function
  override def update: Unit = println(controller.printDisplay)
}
