package de.htwg.se.pokeymon.Model

object Setup {
  // ***********Building a Trainer, Pokemons, and Moves
  val empty_move = Move("empty", 0)
  val tackle = Move("tackle", 50)
  val thunder = Move("thunder", 70)
  val bodyslam = Move("bodyslam", 30)
  val kick = Move("kick", 10)

//***********Pokemons
  val pikachu_moves: List[Move] = List(tackle, thunder)
  val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30)

  val rat_moves: List[Move] = List(bodyslam, kick)
  val ratmon = Pokemon(2, "ratmon", 100, rat_moves, 40)

  val cowPokemon = Pokemon(3, "cowpokemon", 100, List(tackle, thunder), 35)
  val evoli = Pokemon(4, "evoli", 100, rat_moves, 35)

  val trainer_ash = Trainer(Vector())

//**********Build Dummy opponent
  val std_moves: List[Move] = List(tackle)
  val testmon1 = Pokemon(5, "testmon1", 100, std_moves, 50)
  val testmon2 = Pokemon(6, "testmon2", 100, std_moves, 50)
  val testmon3 = Pokemon(7, "testmon3", 100, std_moves, 50)
  val testmon4 = Pokemon(8, "testmon4", 100, std_moves, 50)
  val testmon5 = Pokemon(9, "testmon5", 100, std_moves, 50)
  val testmon6 = Pokemon(10, "testmon6", 100, std_moves, 50)
  val std_mons = Vector(testmon1, testmon2, testmon3, testmon4, testmon5, testmon6)
  val opponent = Trainer(std_mons)

//***********List of all available Pokemon
  val available_pokemon = Vector(pikachu, ratmon, cowPokemon, evoli)
}
