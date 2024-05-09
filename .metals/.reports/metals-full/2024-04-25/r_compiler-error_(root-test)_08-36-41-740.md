file:///C:/Users/Nutzer/Desktop/AIN/AIN3/SoftwareEngineering/Pokeymon/src/test/scala/de/htwg/se/pokeymon/PokeymonSpec.scala
### dotty.tools.dotc.ast.Trees$UnAssignedTypeException: type of Ident(available_pokemon) is not assigned

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 378
uri: file:///C:/Users/Nutzer/Desktop/AIN/AIN3/SoftwareEngineering/Pokeymon/src/test/scala/de/htwg/se/pokeymon/PokeymonSpec.scala
text:
```scala
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

val tackle: Move = Move("Tackle", 10)
val thunder: Move = Move("Thunder", 20)
val pikachu_moves: List[Move] = List(tackle, thunder)
val pikachu: Pokemon = Pokemon(10, "Pikachu", 100, pikachu_moves, 35)
val duck = Pokemon(20, "Duck", 100, pikachu_moves, 35)
val available_pokemon[@@]
class Pokeymon extends AnyWordSpec:
  //______________TRAINER CLASS TEST______________//

  // ---.hasPokemonLeft
  "Trainer " should {

    "return true if he has one Pokeymon left" in {
      val trainer =
        Trainer(List(pikachu))
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

```



#### Error stacktrace:

```
dotty.tools.dotc.ast.Trees$Tree.tpe(Trees.scala:74)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:207)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:104)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:53)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:391)
```
#### Short summary: 

dotty.tools.dotc.ast.Trees$UnAssignedTypeException: type of Ident(available_pokemon) is not assigned