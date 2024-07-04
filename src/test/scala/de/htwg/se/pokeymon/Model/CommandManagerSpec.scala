package de.htwg.se.Pokeymon.Model

import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.Util.Command

class CommandManagerSpec extends AnyWordSpec {

  "A CommandManager" when {
    "executing commands" should {
      "execute the command and update its state" in {
        val manager = new CommandManager


      }
    }

    "undoing commands" should {
      "undo the last executed command" in {
        val manager = new CommandManager


      }
    }

    "redoing commands" should {
      "redo the last undone command" in {
        val manager = new CommandManager


      }
    }
  }

}
