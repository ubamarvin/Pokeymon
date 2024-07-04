package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

// Definition der Chain of Responsibility

trait ChoiceHandler {
  def setSuccessor(successor: ChoiceHandler): ChoiceHandler
  def handleChoice(playersChoice: PlayersChoice): PlayersChoice
}

case class PlayersChoice(player: String, opponent: String, roundReport: String)

case class SwitchPokemonHandler(nextHandler: ChoiceHandler) extends ChoiceHandler {
  override def setSuccessor(successor: ChoiceHandler): ChoiceHandler = this.copy(nextHandler = successor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice = {
    println("SwitchPokemonHandler handling choice")
    playersChoice // Hier könnte die Logik für den Switch enthalten sein
  }
}

case class EvaluateAttackHandler(nextHandler: ChoiceHandler) extends ChoiceHandler {
  override def setSuccessor(successor: ChoiceHandler): ChoiceHandler = this.copy(nextHandler = successor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice = {
    println("EvaluateAttackHandler handling choice")
    playersChoice // Hier könnte die Logik für den Angriff enthalten sein
  }
}

case class StatusHandler() extends ChoiceHandler {
  override def setSuccessor(successor: ChoiceHandler): ChoiceHandler = this

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice = {
    println("StatusHandler handling choice")
    playersChoice // Hier könnte die Logik für Status-Effekte enthalten sein
  }
}

// Test der Chain of Responsibility

class ChoiceHandlerSpec extends AnyFlatSpec with Matchers {

  "A ChoiceHandler chain" should "correctly handle switching Pokémon" in {
    // Setup
    val initialChoice = PlayersChoice("Player1", "Player2", "")

    // Create the chain of responsibility
    val switchHandler = SwitchPokemonHandler(null)
    val evaluateHandler = EvaluateAttackHandler(switchHandler)
    val statusHandler = StatusHandler()
    switchHandler.setSuccessor(evaluateHandler)

    // Handle the choice
    val finalState = statusHandler.handleChoice(initialChoice)

    // Assertions
    finalState.player shouldEqual "Player1"
    finalState.opponent shouldEqual "Player2"
  }

  it should "correctly handle attacks" in {
    // Setup
    val initialChoice = PlayersChoice("Player1", "Player2", "")

    // Create the chain of responsibility
    val switchHandler = SwitchPokemonHandler(null)
    val evaluateHandler = EvaluateAttackHandler(switchHandler)
    val statusHandler = StatusHandler()
    switchHandler.setSuccessor(evaluateHandler)

    // Handle the choice
    val finalState = statusHandler.handleChoice(initialChoice)

    // Assertions
    finalState.player shouldEqual "Player1"
    finalState.opponent shouldEqual "Player2"
  }

  it should "correctly handle status effects" in {
    // Setup
    val initialChoice = PlayersChoice("Player1", "Player2", "")

    // Create the chain of responsibility
    val switchHandler = SwitchPokemonHandler(null)
    val evaluateHandler = EvaluateAttackHandler(switchHandler)
    val statusHandler = StatusHandler()
    switchHandler.setSuccessor(evaluateHandler)

    // Handle the choice
    val finalState = statusHandler.handleChoice(initialChoice)

    // Assertions
    finalState.player shouldEqual "Player1"
    finalState.opponent shouldEqual "Player2"
  }

}
