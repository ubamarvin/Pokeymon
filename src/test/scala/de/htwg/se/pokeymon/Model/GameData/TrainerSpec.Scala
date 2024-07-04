import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData._

class TrainerSpec extends AnyFlatSpec with Matchers {

  "A Trainer" should "add a Pokemon correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(Move("tackle", 50, "normal")), 30, "elektro")
    val trainer = Trainer(Vector.empty)
    val updatedTrainer = trainer.addPokemon(pikachu)
    updatedTrainer.pokemons should contain (pikachu)
  }

  it should "remove a Pokemon correctly by name" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(Move("tackle", 50, "normal")), 30, "elektro")
    val charmander = Pokemon(2, "charmander", 100, List(Move("ember", 50, "fire")), 30, "fire")
    val trainer = Trainer(Vector(pikachu, charmander))
    val updatedTrainer = trainer.removePokemon("charmander")
    updatedTrainer.pokemons should not contain charmander
  }

  it should "switch the current Pokemon correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(Move("tackle", 50, "normal")), 30, "elektro")
    val charmander = Pokemon(2, "charmander", 100, List(Move("ember", 50, "fire")), 30, "fire")
    val trainer = Trainer(Vector(pikachu, charmander), pikachu)
    val updatedTrainer = trainer.switchPokemon(charmander)
    updatedTrainer.currentPokemon shouldEqual charmander
  }

  it should "get a Pokemon by name correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(Move("tackle", 50, "normal")), 30, "elektro")
    val charmander = Pokemon(2, "charmander", 100, List(Move("ember", 50, "fire")), 30, "fire")
    val trainer = Trainer(Vector(pikachu, charmander))
    val foundPokemon = trainer.getPokemonByName("charmander")
    foundPokemon shouldBe Some(charmander)
  }

  it should "check if a Pokemon exists correctly" in {
    val pikachu = Pokemon(1, "pikachu", 100, List(Move("tackle", 50, "normal")), 30, "elektro")
    val charmander = Pokemon(2, "charmander", 100, List(Move("ember", 50, "fire")), 30, "fire")
    val trainer = Trainer(Vector(pikachu, charmander))
    val exists = trainer.pokemonExists("charmander")
    exists shouldBe true
  }

  it should "check if no Pokemon is left correctly" in {
    val pikachu = Pokemon(1, "pikachu", 0, List(Move("tackle", 50, "normal")), 30, "elektro")
    val trainer = Trainer(Vector.empty, pikachu)
    val noPokemonLeft = trainer.hasNoPokemonleft()
    noPokemonLeft shouldBe true
  }

}
