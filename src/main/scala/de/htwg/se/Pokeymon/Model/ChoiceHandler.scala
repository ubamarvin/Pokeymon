package de.htwg.se.Pokeymon.Model

import de.htwg.se.Pokeymon.Model.Setup.opponent

// Chain of Responsibility

// pass down the "PlayersChoice" Object containing the trainers with their choices
case class PlayersChoice(player: Trainer, opponent: Trainer, MSg: String)

/*PlayerChoice will be evaluated in the following order:
    change pokemon
    use item
    move (status or attack
 */

trait ChoiceHandler:
  def setSuccessor(succesor: ChoiceHandler): ChoiceHandler
  def handleChoice(playersChoice: PlayersChoice): PlayersChoice // void..for now

// not prettty
case class SwitchPokemonHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("SwitchPkHandler \n")
    val playerChoice = playersChoice.player.choice
    val opponentChoice = playersChoice.opponent.choice
    val player = playersChoice.player
    val opponent = playersChoice.opponent
    val defaultPokemon = player.currentPokemon

    (playerChoice, opponentChoice) match {
      // Both players switch their Pokémon
      case (Some(pc: SwitchPokemonChoice), Some(oc: SwitchPokemonChoice)) =>
        println("both switch")
        (pc.pokemon, oc.pokemon) match {
          case (Some(playerPokemon), Some(opponentPokemon)) =>
            val upd_player = playersChoice.player.switchPokemon(playerPokemon)
            val upd_opponent = playersChoice.opponent.switchPokemon(opponentPokemon)
            val updchoice = new PlayersChoice(upd_player, upd_opponent, "\nBoth switched their pokemon")
            nextHandler.handleChoice(updchoice)
          case _ =>
            playersChoice
        }

      // Only the player switches their Pokémon
      case (Some(pc: SwitchPokemonChoice), _) =>
        println("player switches")
        pc.pokemon match {
          case Some(playerPokemon) =>
            val upd_player = playersChoice.player.switchPokemon(playerPokemon)
            val updchoice = new PlayersChoice(upd_player, playersChoice.opponent, "\nPlayer switched their pokemon")
            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // Only the opponent switches their Pokémon
      case (_, Some(oc: SwitchPokemonChoice)) =>
        println("opponent switches")
        oc.pokemon match {
          case Some(opponentPokemon) =>
            val upd_opponent = playersChoice.opponent.switchPokemon(opponentPokemon)
            val updchoice = new PlayersChoice(playersChoice.player, upd_opponent, "\nOpponent switched their pokemon")
            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // No switch, pass to the next handler in the chain
      case _ =>
        println("no switch")
        nextHandler.handleChoice(playersChoice)
    }

    // if at least one has not ThisChoic, next handler shall handle

//__There are no Items yet so ill leave this here if i have time to implemt it
case class UseItemHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    println("UseItemHandler \n")

    if (false) { // No Items available yet
      playersChoice
    } else {
      nextHandler.handleChoice(playersChoice)
    }

//__This will be the biggest aids
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
