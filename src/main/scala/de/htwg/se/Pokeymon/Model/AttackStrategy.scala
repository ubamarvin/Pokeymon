package de.htwg.se.Pokeymon.Model
//StrategyPattern
trait BattleMediator {
  def inflictDamage(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String)

}
// either strategy inside the mediator or multiple mediators....
// attacker parameter is not used now but will be in near future for effects like "recoil" or "acid skin"
class AttackMediator extends BattleMediator:
  override def inflictDamage(attacker: Pokemon, defender: Pokemon, move: Move): (Pokemon, Pokemon, String) =
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
        (1.0, " handled normal")
    }

    val damage = move.power * effectiveness
    val upd_defender = defender.decreaseHp(damage.toInt)
    val msg = "\nThe attack " + move.name + " was " + eff + "\n"
    (attacker, upd_defender, msg)
