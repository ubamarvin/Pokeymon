case class Pokemon(name: String, hp: Int, moves: List[Move]):
  def changeHp(newHp: Int): Pokemon = this.copy(hp = hp - newHp)
  def isAlive(): Boolean = hp != 0
  def attack(moveName: String): Option[Int] =
    this.moves.find(_.name == moveName).map(_.power)

case class Move(name: String, power: Int)

val tackle = Move("tackle", 50)
val thunder = Move("thunder", 70)
val pikachuMoves: List[Move] = List(tackle, thunder)
val pikachu = Pokemon("pikachu", 100, pikachuMoves)

val pikachuHurt = pikachu.changeHp(50)
val status = pikachuHurt.isAlive()
val pikachuDead = pikachuHurt.changeHp(50)
val statusDead = pikachuDead.isAlive()
val num = pikachu.moves.find(_.name == "tackle").map(_.power)

val cowPokemon = Pokemon("cowPokemon", 100, List(tackle, thunder))

def battle(pk1: Pokemon, pk2: Pokemon) =
  println(pk1.name + " has " + pk1.hp + " Hp left")
  println(pk2.name + " has " + pk2.hp + " Hp left")
  val playerMove = scala.io.StdIn.readLine()
  val upd_pk2 = pk2.changeHp(pk1.attack(playerMove).getOrElse(0))
  val upd_pk1 = pk1.changeHp(pk2.attack("thunder").getOrElse(0))
  // battle(upd_pk1, upd_pk2)

val battleField =
  List("             ", "             ", "             ", "             ")

