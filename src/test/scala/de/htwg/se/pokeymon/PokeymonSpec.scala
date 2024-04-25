import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

val tackle: Move = Move("tackle", 10)
val thunder: Move = Move("thunder", 20)
val pikachu_moves: List[Move] = List(tackle, thunder)
val pikachu: Pokemon = Pokemon(10, "pikachu", 100, pikachu_moves, 35)
val duck = Pokemon(20, "duck", 100, pikachu_moves, 35)
val available_pokemon: Vector[Pokemon] = Vector(pikachu, duck)
val trainer: Trainer = Trainer(Vector())
val pokedex: Pokedex = Pokedex(Vector(pikachu, duck))

class Pokeymon extends AnyWordSpec:
  // ______________TRAINER CLASS TEST______________//

  // ---.hasPokemonLeft
  "Trainer " should {

    "return true if he has one Pokeymon left" in {
      val trainer =
        Trainer(Vector(pikachu))
      trainer.hasPokemonleft() should be(true)
    }
  }

  "Trainer " should {

    "return true if he has at least one Pokeymon left" in {
      val trainer =
        Trainer(Vector(pikachu, pikachu))
      trainer.hasPokemonleft() should be(true)
    }
  }

  "Trainer " should {

    "return false if he has no Pokeymon left" in {
      val trainer =
        Trainer(Vector())
      trainer.hasPokemonleft() should be(false)
    }
  }
  // .removePokemon  *removes a pokemon by its name, not yet ID

  "Trainer " should {

    "have duck removed from the list" in {
      val trainer = Trainer(Vector(pikachu, duck))
      val trainer_one_pokemon_less = trainer.removePokemon("duck")
      trainer_one_pokemon_less.pokemons.exists(_.name == "duck") should be(
        false
      )
    }
  }

  "Trainer " should {

    "have pikachu in the list when duck is removed" in {
      val trainer = Trainer(Vector(pikachu, duck))
      val trainer_one_pokemon_less = trainer.removePokemon("duck")
      trainer_one_pokemon_less.pokemons.exists(_.name == "pikachu") should be(
        true
      )
    }
  }

  // ---------Pokemon CLASS TEST---------------//

  "Pokemon " should {

    "faint when it has 0 HP left" in {
      val pikachuFaint = pikachu.decreaseHp(100)
      pikachuFaint.isAlive() should be(false)
    }
  }

  "Pokemon " should {

    "be alive when it has Hp >= 1 left" in {
      val pikachuFaint = pikachu.decreaseHp(99)
      pikachuFaint.isAlive() should be(true)
    }
  }

  "Pokemon " should {

    "should return the move specified" in {
      val upd_pikachu = pikachu.setCurrentMove("tackle")
      val move = upd_pikachu.currentMove
      move.name should be("tackle")
    }
  }

  // ______________Class Pokedex_______________________

  "Class Pokedex " should {

    "show all available Pokemon with " in {
      val pokedex = Pokedex(available_pokemon)
      pokedex.showAvailablePokemon() should be("pikachu, duck")
    }
  }

  "Class Pokedex " should {

    "return the pokemon that was chosen " in {
      val pokedex = Pokedex(available_pokemon)
      val (chosenMon, updatedPokedex) = pokedex.choosePokemon("duck")
      chosenMon.name should be("duck")
      updatedPokedex.showAvailablePokemon() should be("pikachu")
    }
  }
