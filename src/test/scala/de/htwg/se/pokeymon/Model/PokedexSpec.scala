import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.Model.{Pokedex, Pokemon, Move}

class PokedexSpec extends AnyWordSpec {

  "A Pokedex" when {
    "created with available Pokemon" should {
      val move1 = Move("Thunderbolt", 90)
      val move2 = Move("Quick Attack", 40)
      val pikachu = Pokemon(1, "Pikachu", 100, List(move1, move2), 50)
      val charmander = Pokemon(2, "Charmander", 100, List(move1, move2), 40)
      val squirtle = Pokemon(3, "Squirtle", 100, List(move1, move2), 45)
      val pokedex = new Pokedex(Vector(pikachu, charmander, squirtle))

      "return a string representation of available Pokemon" in {
        assert(pokedex.showAvailablePokemon() === "Pikachu, Charmander, Squirtle")
      }

      "choose a specified Pokemon and update the Pokedex" in {
        val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("Charmander")
        assert(chosenPokemon.name === "Charmander")
        assert(updatedPokedex.showAvailablePokemon() === "Pikachu, Squirtle")
      }

      "check if a Pokemon exists in the Pokedex" in {
        assert(pokedex.exists("Pikachu"))
        assert(!pokedex.exists("Bulbasaur")) // Assuming Bulbasaur is not in the initial Pokedex
      }
    }
  }

}
