import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

val tackle: Move = Move("Tackle", 10)
val thunder: Move = Move("Thunder", 20)
val pikachu_moves: List[Move] = List(tackle, thunder)
val pikachu: Pokemon = Pokemon("Pikachu", 100, pikachu_moves)
val duck = Pokemon("Duck", 100, pikachu_moves)
class Pokeymon extends AnyWordSpec:
  // ---------TRAINER CLASS TEST---------------//

  // ---.hasPokemonLeft
  "Trainer " should {

    "return true if he has one Pokeymon left" in {
      val trainer =
        Trainer(List(Pokemon("Pikachu,", 100, List(Move("Thunder", 50)))))
      trainer.hasPokemonleft() should be(true)
    }
  }

  "Trainer " should {

    "return true if he has at least one Pokeymon left" in {
      val trainer =
        Trainer(List(pikachu, pikachu))
      trainer.hasPokemonleft() should be(true)
    }
  }

  "Trainer " should {

    "return false if he has no Pokeymon left" in {
      val trainer =
        Trainer(List())
      trainer.hasPokemonleft() should be(false)
    }
  }
  // .removePokemon  *removes a pokemon by its name, not yet ID

  "Trainer " should {

    "have duck removed from the list" in {
      val trainer = Trainer(List(pikachu, duck))
      val trainer_one_pokemon_less = trainer.removePokemon("Duck")
      trainer_one_pokemon_less.pokemons.exists(_.name == "Duck") should be(
        false
      )
    }
  }

  "Trainer " should {

    "have pikachu in the list when duck is removed" in {
      val trainer = Trainer(List(pikachu, duck))
      val trainer_one_pokemon_less = trainer.removePokemon("Duck")
      trainer_one_pokemon_less.pokemons.exists(_.name == "Pikachu") should be(
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
