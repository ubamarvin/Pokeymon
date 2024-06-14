package de.htwg.se.Pokeymon

import scala.concurrent.{Future, ExecutionContext}
import de.htwg.se.Pokeymon.Controller._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerBaseImplemtation.Controller
import de.htwg.se.Pokeymon.aView.Tui
import de.htwg.se.Pokeymon.Model.GameComponent.Game
import de.htwg.se.Pokeymon.aView.Gui.PokeGui

object pokeymon {
  // val game = new Game
  val controller = new Controller(new Game) // why new? //

  val tui = new Tui(controller)

  val PokeGui = new PokeGui(controller) // Init Gui and give it power!

  // PokeGui.start()
  controller.notifyObservers // Warum, for the first render!!!

  @main
  def startGame(): Unit =
    /*
    new Thread(() => { // does not run concurrently
      PokeGui.main(Array.empty)
    }).start()
     */
    // Needed for Concurrentcy, gippity it
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
