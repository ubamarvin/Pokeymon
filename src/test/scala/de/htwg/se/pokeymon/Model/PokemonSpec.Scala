import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PokemonSpec extends AnyWordSpec {
  import de.htwg.se.Pokeymon.Model._

  "A Pokemon" when {
    val move1 = Move("Tackle", 20)
    val move2 = Move("Thunderbolt", 40)
    val moves = List(move1, move2)
    val pokemon = Pokemon(1, "Pikachu", 100, moves, 50)

    "decreasing HP" should {
      "return a new Pokemon with decreased HP" in {
        val newPokemon = pokemon.decreaseHp(20)
        assert(newPokemon.hp == 80)
      }

      "remain alive if HP is above 0" in {
        val newPokemon = pokemon.decreaseHp(50)
        assert(newPokemon.isAlive())
      }

      "be dead if HP reaches 0" in {
        val newPokemon = pokemon.decreaseHp(100)
        assert(!newPokemon.isAlive())
      }
    }

    "attacking with a move" should {
      "return the power of the move if it exists" in {
        val power = pokemon.attack("hunderbolt")
        assert(power.contains(40))
      }

      "return None if the move does not exist" in {
        val power = pokemon.attack("NonExistentMove")
        assert(power.isEmpty)
      }
    }

    "setting current move" should {
      "return a new Pokemon with the specified current move" in {
        val newPokemon = pokemon.setCurrentMove("Thunderbolt")
        assert(newPokemon.currentMove == move2)
      }

    }
  }
}
