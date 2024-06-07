package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Setup._
import de.htwg.se.Pokeymon.Model.Move
import de.htwg.se.Pokeymon.Model.StatusStrategy

//_____________Class Pokemon____________________
case class Pokemon(
    id: Int,
    name: String,
    hp: Int = 100,
    moves: List[Move],
    speed: Int,
    pokeType: String,
    currentMove: Move = empty_move,
    status: StatusEffectStrategyContext = new StatusEffectStrategyContext(),
    canMove: Boolean = true // if status is sleep,.. it shouldnt be able to move. but of course that information is in the status itself
): // extendWith ID, Stats, type, and status
  def decreaseHp(damage: Int): Pokemon = this.copy(hp = hp - damage)

  def isAlive(): Boolean = hp > 0

  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)

  def setStatus(newStatus: StatusStrategy): Pokemon =
    val newContext = status.setStatusContext(newStatus)
    this.copy(status = newContext)

  def setCurrentMove(new_move: String): Pokemon =
    val move = this.moves.find(_.name == new_move).getOrElse(empty_move)
    this.copy(currentMove = move)

  def movesToString(): String =
    moves.map(_.moveToString()).mkString("\n")

  def getMoves(): List[Move] =
    moves

  def getHp(): Int =
    this.hp

  def getStatus(): String =
    status.getStatusName()

  override def toString: String =
    this.name + " Hp: " + this.hp + " Status: " + this.status.getStatusName()

  // eine Attacke kann entweder ein Hp Move sein oder ein Status veränderndern Move sein, hier soll das geregelt werden
  // def receiveAttack(move)

  // def changeStatus
