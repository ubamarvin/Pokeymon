package de.htwg.se.Pokeymon.Model

import de.htwg.se.Pokeymon.Model.Setup.opponent

// Chain of Responsibility

// pass down the "PlayersChoice" Object containing the trainers with their choices
case class PlayersChoice(player: Trainer, playerMsg: String, opponent: Trainer, oppMSg: String, printOrder: Int)

/*PlayerChoice will be evaluated in the following order:
    change pokemon
    use item
    move (status or attack
 */

trait ChoiceHandler:
  def setSuccessor(succesor: ChoiceHandler): ChoiceHandler
  def handleChoice(playersChoice: PlayersChoice): PlayersChoice // void..for now

//
case class SwitchPokemonHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("SwitchPkHandler \n")
    // identify Choice
    // Perform Choice
    // UpdateModel
    // if at least one has not ThisChoic, next handler shall handle
    if (true) { //
      playersChoice
    } else {
      nextHandler.handleChoice(playersChoice)
    }

//____________________________________________________________________________
case class UseItemHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("UseItemHandler \n")
    // identify Choice
    // Perform Choice
    // UpdateModel
    // if at least one has not ThisChoic, next handler shall handle
    if (true) { //
      playersChoice
    } else {
      nextHandler.handleChoice(playersChoice)
    }

case class EvaluateAttackHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("EvalMoveHandler \n")
    // identify Choice
    // Perform Choice
    // UpdateModel
    // if at least one has not ThisChoic, next handler shall handle
    if (true) { //
      playersChoice
    } else {
      nextHandler.handleChoice(playersChoice)
    }

//no class var because this is the last handler, for now
case class StatusHandler() extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    this

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("StatusHandler \n")
    // identify Choice
    // Perform Choice
    // UpdateModel
    //
    playersChoice
