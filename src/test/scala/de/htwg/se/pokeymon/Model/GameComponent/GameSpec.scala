package de.htwg.se.Pokeymon.Model.GameComponent

import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec with Matchers {

  "A Game" when {
    "initialized" should {
      val initialState = PickPokemonState(Trainer(Vector()), Setup.pokedex, 0, Setup.opponent)

      "handle input correctly" in {
        val game = Game(initialState)

        // Testen des Handlings von Eingaben
        val nextState = game.handleInput("some input")
        nextState.state should not be initialState
      }

      "handle undo/redo correctly" in {
        val game = Game(initialState)

        // Testen des Undo-Mechanismus
        val nextState = game.handleInput("some input")
        val prevState = game.gameUndo()

        prevState.state should be(initialState)

        // Testen des Redo-Mechanismus
        val redoState = prevState.gameRedo()
        redoState.state should be(nextState.state)
      }

      "notify observers on change" in {
        val game = Game(initialState)

        class TestObserver extends Observer {
          var notified = false
          override def update: Unit = notified = true
        }

        val observer = new TestObserver
        // Testen der Benachrichtigung von Beobachtern
        game.handleInput("some input")
        observer.notified should be(true)
      }
    }
  }
}
