package de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.Move
import de.htwg.se.Pokeymon.Model.Pokemon
import de.htwg.se.Pokeymon.Model.Pokedex

object Setup {
  // ***********Building a Trainer, Pokemons, and Moves
  val empty_move = Move("empty", 0, "normal")
  val tackle = Move("tackle", 50, "normal")
  val thunder = Move("thunder", 70, "elektro")
  val bodyslam = Move("bodyslam", 30, "normal")
  val kick = Move("kick", 75, "normal")
  val waterJet = Move("waterjet", 50, "water")
  val fireBreath = Move("firebreath", 50, "fire")
  val vegankick = Move("vegankick", 50, "plant")
  val burn = Move("burn", 0, "fire", "burn")

//***********Pokemons
  val pikachu_moves: List[Move] = List(tackle, thunder)
  val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30, "elektro")

  val fire_moves: List[Move] = List(fireBreath, burn)
  val firefox = Pokemon(12, "firefox", 100, fire_moves, 100, "fire")

  val fish_moves: List[Move] = List(waterJet, vegankick)
  val fish = Pokemon(11, "fish", 100, fish_moves, 75, "water")

  val rat_moves: List[Move] = List(bodyslam, kick)
  val ratmon = Pokemon(2, "ratmon", 100, rat_moves, 40, "normal")

  val cowPokemon = Pokemon(3, "cowpokemon", 100, List(tackle, thunder), 35, "normal")
  val evoli = Pokemon(4, "evoli", 100, rat_moves, 35, "fight")

  val trainer_ash = Trainer(Vector())

//**********Build Dummy opponent
  val std_moves: List[Move] = List(tackle)
  val testmon1 = Pokemon(5, "waterPk", 100, std_moves, 50, "water")
  val testmon2 = Pokemon(6, "firePk", 100, std_moves, 50, "fire")
  val testmon3 = Pokemon(7, "elektroPk", 100, std_moves, 50, "elektro")
  val testmon4 = Pokemon(8, "plantPk", 100, std_moves, 50, "plant")
  val testmon5 = Pokemon(9, "nomralPk", 100, std_moves, 50, "normal")
  val testmon6 = Pokemon(10, "fightPk", 100, std_moves, 50, "fight")
  // val std_mons = Vector(testmon1, testmon2, testmon3, testmon4, testmon5, testmon6)
  val std_mons = Vector(testmon1)
  val opponent = Trainer(std_mons)

//***********List of all available Pokemon
  val available_pokemon = Vector(pikachu, ratmon, cowPokemon, evoli, fish, firefox)
  val pokedex = Pokedex(available_pokemon)
}
