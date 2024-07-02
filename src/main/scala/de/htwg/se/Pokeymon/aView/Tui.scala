package de.htwg.se.Pokeymon.aView

import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.Controller

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInput(input: String): Unit = {

    input match {
      case "q" =>
        println("Game quitted")
      case "z" => controller.undo
      case "y" => controller.redo
      case "save" => controller.save
      case "load" => controller.load
      case _   => controller.handleInput(input)
    }
  }
  override def update: Unit = println(controller.printDisplay())
}
