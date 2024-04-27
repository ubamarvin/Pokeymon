package de.htwg.se.pokeymon.aView

import de.htwg.se.pokeymon.util.Observer
import de.htwg.se.pokeymon.Controller.Controller

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInput(input: String): Unit = {
    input match {
      case "q" => println("Fuck")

    }
  }

  // override def update: Unit =  println(controller.gridToString)
  // update must also communicate game state
  // das die vom observable gerufende update function
  // sie aktuallisiert das Spiel(Feld)
  override def update: Unit = println("8==D~~~~")
}

//override def update: Unit = println(controller.Display())
