package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CommandManagerSpec extends AnyWordSpec {

  "A CommandManager" should {

    "execute a command with doStep" in {
      val manager = new CommandManager
      var executed = false


    }

    "undo a command with undoStep" in {
      val manager = new CommandManager
      var undone = false

    }

    "redo a command with redoStep" in {
      val manager = new CommandManager
      var redone = false

  }
}
}