package de.htwg.se.Pokeymon.Model.GameData
import de.htwg.se.Pokeymon.Model.GameData.Move
import de.htwg.se.Pokeymon.Model.GameData.Pokemon
import de.htwg.se.Pokeymon.Model.GameData.Pokedex
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._

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
  val bsys_testat = ("burn", 75, "fire", "burn")


//***********Pokemons
  val pikachu_moves: List[Move] = List(tackle, thunder)
  val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30, "elektro")

  val fire_moves: List[Move] = List(fireBreath, burn)
  val charizard = Pokemon(12, "charizard", 100, fire_moves, 100, "fire")

  val fish_moves: List[Move] = List(waterJet, vegankick)
  val wartortle = Pokemon(11, "wartortle", 100, fish_moves, 75, "water")

  val rat_moves: List[Move] = List(bodyslam, kick)
  val meowth = Pokemon(2, "Meowth", 100, rat_moves, 40, "normal")

  val arbok = Pokemon(3, "Arbok", 100, List(tackle, thunder), 35, "normal")
  val evoli = Pokemon(4, "eevee", 100, rat_moves, 35, "fight")

  val charmander_moves: List[Move] = List(fireBreath, tackle)
  val charmander = Pokemon(5, "charmander", 90, charmander_moves, 50, "fire")

  val bulbasaur_moves: List[Move] = List(tackle, vegankick)
  val bulbasaur = Pokemon(6, "bulbasaur", 95, bulbasaur_moves, 45, "plant")

  val squirtle_moves: List[Move] = List(waterJet, tackle)
  val squirtle = Pokemon(7, "squirtle", 90, squirtle_moves, 40, "water")

  val raichu_moves: List[Move] = List(thunder, bodyslam)
  val raichu = Pokemon(8, "Raichu", 105, raichu_moves, 50, "elektro")

  val oddish_moves: List[Move] = List(vegankick, tackle)
  val oddish = Pokemon(9, "oddish", 85, oddish_moves, 30, "plant")

  val zubat_moves: List[Move] = List(tackle, bodyslam)
  val zubat = Pokemon(10, "zubat", 80, zubat_moves, 25, "normal")

  val psyduck_moves: List[Move] = List(waterJet, bodyslam)
  val psyduck = Pokemon(13, "psyduck", 90, psyduck_moves, 45, "water")

  val primeape_moves: List[Move] = List(kick, tackle)
  val primeape = Pokemon(14, "primeape", 110, primeape_moves, 60, "fight")

  val gloom_moves: List[Move] = List(vegankick, tackle)
  val gloom = Pokemon(15, "gloom", 100, gloom_moves, 35, "plant")

  val golduck_moves: List[Move] = List(waterJet, bodyslam)
  val golduck = Pokemon(16, "golduck", 100, golduck_moves, 55, "water")

  val venusaur_moves: List[Move] = List(vegankick, tackle)
  val venusaur = Pokemon(17, "venusaur", 120, venusaur_moves, 70, "plant")

  

  val trainer_ash = Trainer(1,Vector())

//**********Build Dummy opponent
  val std_moves: List[Move] = List(tackle)
  val lugia = Pokemon(5, "lugia", 100, std_moves, 50, "water")
  
  // val std_mons = Vector(testmon1, testmon2, testmon3, testmon4, testmon5, testmon6)
  val std_mons = Vector(lugia)
  val opponent = Trainer(2,std_mons)

  val available_pokemon = Vector(bulbasaur,squirtle,raichu ,oddish,zubat,psyduck ,primeape ,gloom,
  golduck,venusaur,pikachu, meowth, arbok, evoli, wartortle, charizard)

//***********List of all available Pokemon
  val pokedex = Pokedex(available_pokemon)
}
