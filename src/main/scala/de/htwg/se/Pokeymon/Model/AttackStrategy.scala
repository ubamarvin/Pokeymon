package de.htwg.se.Pokeymon.Model
//StrategyPattern
// Why this Patter?
// Because i may extend the attack strategies
// doing it with strategy allows me to add strategy without channgin any code in the game control flow
// for example one could add attacks that both damage and change the status of defender
// or attacks that have recoil
trait AttackStrategy {
  def applyMove(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String)

}

case class AttackStrategyContext(attackStrategy: AttackStrategy = new applyDamageStrategy()) {
  // delegate the move to the aggregated class
  def applyAttackStrategy(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String) =
    attackStrategy.applyMove(attacker, defender, move)

  // sets the context based on MOve Type
  def setContext(move: Move): AttackStrategyContext = {
    val newAttackStrategy =
      if (move.statusEffect == "none") new applyDamageStrategy()
      else new applyStatusChangeStrategy()
    copy(attackStrategy = newAttackStrategy)
  }

}
// either strategy inside the mediator or multiple mediators....
// attacker parameter is not used now but will be in near future for effects like "recoil" or "acid skin"
class applyDamageStrategy extends AttackStrategy:
  override def applyMove(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String) =
    val moveType = move.moveType
    val defenderType = defender.pokeType
    val e = "effective"
    val nE = "not effective"
    val (effectiveness: Double, eff: String) = (moveType, defenderType) match {
      case ("normal", "fight")  => (0.5, nE)
      case ("fight", "normal")  => (1.5, e)
      case ("fire", "water")    => (0.5, nE)
      case ("fire", "plant")    => (0.5, nE)
      case ("water", "fire")    => (1.5, e)
      case ("water", "plant")   => (0.5, nE)
      case ("plant", "fire")    => (0.5, nE)
      case ("plant", "water")   => (1.5, e)
      case ("plant", "elektro") => (1.5, e)
      case ("elektro", "water") => (1.5, e)
      case _ =>
        (1.0, "executed normally")
    }

    val damage = move.power * effectiveness
    val upd_defender = defender.decreaseHp(damage.toInt)
    val msg = "-" + attacker.name + "s attack " + move.name + " was " + eff + "\n"
    (attacker, upd_defender, msg)

class applyStatusChangeStrategy extends AttackStrategy:
  override def applyMove(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String) =
    val statusChange = move.statusEffect

    val (updDefender, msg: String) = statusChange match {
      case "burn" =>
        val updDefender = defender.setStatus(new BurnedState(3))
        val msg = "-" + defender.name + " got burned \n"
        (updDefender, msg)

      case "poison" =>
        val updDefender = defender.setStatus(new BurnedState(3))
        val msg = defender.name + " got poisoned \n"
        (updDefender, msg)

      case "freez" =>
        val updDefender = defender.setStatus(new BurnedState(3))
        val msg = "-" + defender.name + " got frozen \n"
        (updDefender, msg)

      case "paralyze" =>
        val updDefender = defender.setStatus(new BurnedState(3))
        val msg = "-" + defender.name + " got paralyzed \n"
        (updDefender, msg)

      case _ =>
        val updDefender = defender
        (updDefender, "-" + attacker.name + "s attack hadno Effect \n")
    }

    (attacker, updDefender, msg)
