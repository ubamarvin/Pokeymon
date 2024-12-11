package de.htwg.se.Pokeymon

import com.google.inject.Guice

import scala.concurrent.{Future, ExecutionContext}
import de.htwg.se.Pokeymon.Controller._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplementation.Controller
import de.htwg.se.Pokeymon.aView.Tui
import de.htwg.se.Pokeymon.Model.GameComponent.Game

object Pokeymon {

  val injector = Guice.createInjector(new PokeymonModule)
  val controller = injector.getInstance(classOf[ControllerInterface])

  val tui = new Tui(controller)
  

  controller.notifyObservers

  
  def main(args: Array[String]): Unit =

    var input: String = ""

    while (input != "q") {
      input = scala.io.StdIn.readLine()
      tui.processInput(input)
    }

}
