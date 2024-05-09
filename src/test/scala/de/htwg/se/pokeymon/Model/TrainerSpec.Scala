import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.Pokeymon.Model._

class TrainerSpec extends AnyWordSpec {

  "A Trainer" when {

    "having pokemons" should {
      val pikachu = Pokemon(1, "Pikachu", 100, List.empty, 10)
      val charmander = Pokemon(2, "Charmander", 100, List.empty, 10)
      val trainer = Trainer(Vector(pikachu, charmander))

      "be able to remove a Pokemon" in {
        val updatedTrainer = trainer.removePokemon("Pikachu")
        updatedTrainer.pokemons should have size 1
        updatedTrainer.pokemons.headOption should contain(charmander)
      }

      "be able to add a Pokemon" in {
        val bulbasaur = Pokemon(3, "Bulbasaur", 100, List.empty, 10)
        val updatedTrainer = trainer.addPokemon(bulbasaur)
        updatedTrainer.pokemons should have size 3
        updatedTrainer.pokemons.lastOption should contain(bulbasaur)
      }

      "be able to set current Pokemon" in {
        val squirtle = Pokemon(4, "Squirtle", 100, List.empty, 10)
        val updatedTrainer = trainer.setCurrentPokemon(squirtle)
        updatedTrainer.currentPokemon shouldEqual squirtle
      }
    }

    "having no pokemons" should {
      val trainer = Trainer(Vector.empty)

      "return false when checking if there are any pokemons left" in {
        trainer.hasPokemonleft() shouldBe false
      }
    }

  }
  "A Trainer" when {

    "having pokemons" should {
      val pikachu = Pokemon(1, "Pikachu", 100, List.empty, 10)
      val charmander = Pokemon(2, "Charmander", 100, List.empty, 10)
      val squirtle = Pokemon(3, "Squirtle", 100, List.empty, 10)
      val trainer = Trainer(Vector(pikachu, charmander, squirtle))

      "be able to get next Pokemon" in {
        val nextPokemon = trainer.getNextPokemon()
        nextPokemon shouldEqual pikachu
      }

      "have a string representation showing remaining pokemons" in {
        val toStringResult = trainer.toString
        toStringResult should include("Remaining Pokemons")
        toStringResult should include("Pikachu")
        toStringResult should include("Charmander")
        toStringResult should include("Squirtle")
      }
    }

    "having no pokemons left" should {
      val trainer = Trainer(Vector.empty)

      "return false when checking if there are any pokemons left" in {
        trainer.hasPokemonleft() shouldBe false
      }

      "return an appropriate string representation" in {
        val toStringResult = trainer.toString
        toStringResult should include("Remaining Pokemons: ")
      }
    }

  }
}
