package de.htwg.se.Pokeymon

import scala.concurrent.{Future, ExecutionContext}
import de.htwg.se.Pokeymon.Controller._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplementation.Controller
import de.htwg.se.Pokeymon.aView.Tui
import de.htwg.se.Pokeymon.Model.GameComponent.Game
import de.htwg.se.Pokeymon.aView.Gui.PokeGui
import com.google.inject.Guice

object pokeymon {

  val injector = Guice.createInjector(new PokeymonModule)
  val controller = injector.getInstance(classOf[ControllerInterface])

  val tui = new Tui(controller)
  val PokeGui = new PokeGui(controller) // Init Gui and give it power!

  controller.notifyObservers // Warum, for the first render!!!

  @main
  def startGame(): Unit =

    import ExecutionContext.Implicits.global

    // Start the GUI in a Concurrent Thread
    val guiFuture: Future[Unit] = Future {
      PokeGui.main(Array.empty)
    }

    var input: String = ""

    while (input != "q") {
      input = scala.io.StdIn.readLine()
      tui.processInput(input)
    }
    // guiFuture.onComplete(_ => println("GUI terminated"))

}
