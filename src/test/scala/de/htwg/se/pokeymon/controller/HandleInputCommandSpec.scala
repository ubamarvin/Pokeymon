package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class HandleInputCommandSpec extends AnyWordSpec with Matchers {

  // Dummy implementations of Controller and Game for testing
  class DummyGame extends Game {
    var inputHandled: Boolean = false
    var undoCalled: Boolean = false
    var redoCalled: Boolean = false

    override def handleInput(input: String): Game = {
      inputHandled = true
      this
    }

    override def gameUndo(): Game = {
      undoCalled = true
      this
    }

    override def gameRedo(): Game = {
      redoCalled = true
      this
    }
  }
}