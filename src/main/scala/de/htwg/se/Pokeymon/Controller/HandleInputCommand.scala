package de.htwg.se.Pokeymon.Controller

import de.htwg.se.Pokeymon.Util.Command

class HandleInputCommand(input: String, controller: Controller) extends Command {
  override def executeStep: Unit =
    controller.game = controller.game.handleInput(input)

  override def undoStep: Unit =
    println("undo in command")
    controller.game = controller.game.gameUndo() // Loads prev gameState in Game and return Game

  override def redoStep: Unit =
    println("redo in command")
    controller.game = controller.game.gameRedo() // ...
}
