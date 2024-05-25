package de.htwg.se.Pokeymon.Model

import de.htwg.se.Pokeymon.Model.Setup.opponent

// Chain of Responsibility

// pass down the "PlayersChoice" Object containing the trainers with their choices
// Als eigene Klasse, da so erweiterbar
case class PlayersChoice(player: Trainer, opponent: Trainer, roundReport: String)

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
    // println("SwitchPkHandler \n")
    val playerChoice = playersChoice.player.choice
    val opponentChoice = playersChoice.opponent.choice
    val player = playersChoice.player
    val opponent = playersChoice.opponent
    val defaultPokemon = player.currentPokemon
    val oldPlayerMon = player.currentPokemon
    val oldOppMon = opponent.currentPokemon

    (playerChoice, opponentChoice) match {
      // Both players switch their Pokémon
      case (Some(pc: SwitchPokemonChoice), Some(oc: SwitchPokemonChoice)) =>
        // println("both switch")
        (pc.pokemon, oc.pokemon) match {
          case (Some(playerPokemon), Some(opponentPokemon)) =>
            val upd_player = playersChoice.player.switchPokemon(playerPokemon)
            val upd_opponent = playersChoice.opponent.switchPokemon(opponentPokemon)
            val msgPlayer = "Player chooses " + playerPokemon.name + " to fight!\n"
            val msgOpp = "Opponent chooses " + opponentPokemon.name + " to fight!\n"
            val updchoice = new PlayersChoice(upd_player, upd_opponent, msgPlayer + msgOpp)
            nextHandler.handleChoice(updchoice)
          case _ =>
            playersChoice
        }

      // Only the player switches their Pokémon
      case (Some(pc: SwitchPokemonChoice), _) =>
        // println("player switches")
        pc.pokemon match {
          case Some(playerPokemon) =>
            val upd_player = playersChoice.player.switchPokemon(playerPokemon)
            val msgPlayer = "Player chooses " + playerPokemon.name + " to fight!\n"
            val updchoice = new PlayersChoice(upd_player, playersChoice.opponent, msgPlayer)
            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // Only the opponent switches their Pokémon
      case (_, Some(oc: SwitchPokemonChoice)) =>
        // println("opponent switches")
        oc.pokemon match {
          case Some(opponentPokemon) =>
            val upd_opponent = playersChoice.opponent.switchPokemon(opponentPokemon)
            val msgOpp = "Opponent chooses " + opponentPokemon.name + " to fight!\n"

            val updchoice = new PlayersChoice(playersChoice.player, upd_opponent, msgOpp)
            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // No switch, pass to the next handler in the chain
      case _ =>
        // println("no switch")
        nextHandler.handleChoice(playersChoice)
    }

    // if at least one has not ThisChoic, next handler shall handle

//__There are no Items yet so ill leave this here if i have time to implemt it
case class UseItemHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    // println("UseItemHandler \n")

    if (false) { // No Items available yet
      playersChoice
    } else {
      nextHandler.handleChoice(playersChoice)
    }

//__This will be the biggest aids
case class EvaluateAttackHandler(nextHandler: ChoiceHandler) extends ChoiceHandler:

  // They do what they say, refactor into 1 function
  private def determineSlowerPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val secondMover = if (pk1.speed <= pk2.speed) pk1 else pk2
    secondMover

  private def determineFasterPokemon(pk1: Pokemon, pk2: Pokemon): Pokemon =
    val firstMover = if (pk1.speed > pk2.speed) pk1 else pk2
    firstMover

  // Need this to identify which pokemon is which
  def matchPokemonById(pk1: Pokemon, pk2: Pokemon, playerPkId: Int, oppPkId: Int): (Pokemon, Pokemon) =
    val playerMon = if (pk1.id == playerPkId) pk1 else pk2
    val oponnentMon = if (pk2.id == oppPkId) pk2 else pk1
    (playerMon, oponnentMon)

  // If one dies mid battle this is used to AutoSwitch
  def switchIfdead(trainer: Trainer): (Trainer) =
    val currentPokemon = trainer.currentPokemon
    if (!currentPokemon.isAlive() && trainer.pokemons.size >= 1) {
      val trainer_rem = trainer.removePokemon(currentPokemon.name)
      val next_pokemon = trainer_rem.getNextPokemon()
      val upd_trainer = trainer_rem.setCurrentPokemon(next_pokemon)
      // println("\nfirst case\n")
      upd_trainer
    } else if (!currentPokemon.isAlive() && trainer.pokemons.size == 10) {
      // println("\nsecond case\n")
      val upd_Trainer = trainer.removePokemon(currentPokemon.name)
      (upd_Trainer)
    } else {
      // println("\nthird case\n")
      trainer
    }

  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    copy(nextHandler = succesor)

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    // println("EvalMoveHandler \n")
    val playerChoice = playersChoice.player.choice
    val opponentChoice = playersChoice.opponent.choice
    val player = playersChoice.player
    val opponent = playersChoice.opponent
    val playerMon = player.currentPokemon
    val oppMon = opponent.currentPokemon
    val report = playersChoice.roundReport

    // instanciate AttackContextClass
    val attackContext = new AttackStrategyContext()

    (playerChoice, opponentChoice) match {
      // Both players switch their Pokémon
      case (Some(pc: AttackChoice), Some(oc: AttackChoice)) =>
        // println("both Attack")
        (pc.move, oc.move) match {
          case (Some(playerMove), Some(opponentMove)) =>
            // get Pokemon Id for identification
            val playerPkId = playerMon.id
            val oppPkId = oppMon.id
            // determine faster pokemon
            val first = determineFasterPokemon(playerMon, oppMon)
            val second = determineSlowerPokemon(playerMon, oppMon)
            // Faster Pokemon attacks slower onea
            // Set the Context / which attack Strategy to use
            val updAttackContext = attackContext.setContext(first.currentMove) // not PlayerMove
            val (firstPk, secondPk, msgOne) = updAttackContext.applyAttackStrategy(first, second, playerMove)
            // Set the attack Context for the slower Mover
            val SecondMoverAttackContext = updAttackContext.setContext(second.currentMove) /// Not player Move
            // Slower Pokemon attacks Faster one only if Slower is alive, else "nothing happens"
            val (updFirstPk, updSecondPk, msgTwo) =
              if (secondPk.isAlive()) then SecondMoverAttackContext.applyAttackStrategy(secondPk, firstPk, opponentMove)
              else (firstPk, secondPk, secondPk.name + " fainted!")

            // Match Pokemons by Id and link them back to their trainers
            val (updPlayerMon, updOppMon) = matchPokemonById(updFirstPk, updSecondPk, playerPkId, oppPkId)
            // Now check if one has died
            val updPlayer = switchIfdead(player.updateCurrentPokemon(updPlayerMon))
            val updOpp = switchIfdead(opponent.updateCurrentPokemon(updOppMon))
            val updPlayersChoice = new PlayersChoice(updPlayer, updOpp, report + msgOne + msgTwo)
            nextHandler.handleChoice(updPlayersChoice)

          case _ =>
            playersChoice
        }

      // Only the player attacks
      case (Some(pc: AttackChoice), _) =>
        // println("player attacks")
        pc.move match {
          case Some(move) =>
            // Set and apply Attack Context
            val updAttackContext = attackContext.setContext(move)
            val (updPlayerMon, updOppMon, msg) = updAttackContext.applyAttackStrategy(playerMon, oppMon, move)
            val updPlayer = player.updateCurrentPokemon(updPlayerMon)
            val updOpp = opponent.updateCurrentPokemon(updOppMon)
            val updchoice = new PlayersChoice(updPlayer, updOpp, report + msg)
            println(msg)
            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // Only the opponent switches their Pokémon
      case (_, Some(oc: AttackChoice)) =>
        // println("opponent attacks")
        oc.move match {
          case Some(move) =>
            val updAttackContext = attackContext.setContext(move)
            val (updOppMon, updPlayerMon, msg) = updAttackContext.applyAttackStrategy(oppMon, playerMon, move)
            val updPlayer = player.updateCurrentPokemon(updPlayerMon)
            val updOpp = opponent.updateCurrentPokemon(updOppMon)
            val updchoice = new PlayersChoice(updPlayer, updOpp, report + msg)
            println(msg)

            nextHandler.handleChoice(updchoice)
          case None =>
            playersChoice
        }

      // No switch, pass to the next handler in the chain
      case _ =>
        // println("no attacks")
        nextHandler.handleChoice(playersChoice)
    }

//This is the last handler
//Status effects burned or poisoned are  applied last
case class StatusHandler() extends ChoiceHandler:
  override def setSuccessor(succesor: ChoiceHandler): ChoiceHandler =
    this

  override def handleChoice(playersChoice: PlayersChoice): PlayersChoice =
    val player = playersChoice.player
    val opponent = playersChoice.opponent
    val playerMon = player.currentPokemon
    val oppMon = opponent.currentPokemon
    val report = playersChoice.roundReport
    val (upd_playerMon, msgOne) = playerMon.status.applyEffect(playerMon)
    val (upd_oppMon, msgTwo) = oppMon.status.applyEffect(oppMon)
    val updPlayer = player.updateCurrentPokemon(upd_playerMon)
    val updOpp = opponent.updateCurrentPokemon(upd_oppMon)

    new PlayersChoice(updPlayer, updOpp, report + msgOne + msgTwo)
