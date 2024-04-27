package de.htwg.se.pokeymon.Model
import de.htwg.se.pokeymon.Model.Setup._

//_____________Class Pokemon____________________
case class Pokemon(id: Int, name: String, hp: Int = 100, moves: List[Move], speed: Int, currentMove: Move = empty_move): // extendWith ID, Stats, type, and status
  def decreaseHp(damage: Int): Pokemon = this.copy(hp = hp - damage)

  def isAlive(): Boolean = hp > 0

  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)

  def setCurrentMove(new_move: String): Pokemon =
    val move = this.moves.find(_.name == new_move).getOrElse(empty_move)
    this.copy(currentMove = move)

  override def toString: String =
    val moveNames = moves.map(_.name).mkString(", ")
    s"$name, $hp, [$moveNames], $speed"

  // eine Attacke kann entweder ein Hp Move sein oder ein Status veränderndern Move sein, hier soll das geregelt werden
  // def receiveAttack(move)

  // def changeStatus
