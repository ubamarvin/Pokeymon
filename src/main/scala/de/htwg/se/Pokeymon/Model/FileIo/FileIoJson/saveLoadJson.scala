/*
package de.htwg.se.Pokeymon.Model.FileIo.FileIoJson

//import com.google.inject.Guice
//import com.google.inject.name.Names
//import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.Pokeymon.Model._
import de.htwg.se.Pokeymon.Model.FileIo.FileIOInterface
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.GameComponent.Game._
import de.htwg.se.Pokeymon.Model.GameComponent.{
  YourDeadState,
  ChooseItemState,
  ChooseAttackState,
  MainState,
  BattleEvalState,
  SwitchPokemonState,
  PickPokemonState,
  GameState
}
import de.htwg.se.Pokeymon.Model.GameComponent.{ItemChoice, AttackChoice, SwitchPokemonChoice, Choice}
import de.htwg.se.Pokeymon.Model.GameData.{Trainer, Pokemon, Move, Pokedex, Item}
import play.api.libs.json._
import play.api.libs.functional.syntax._

class FileIOXml extends FileIOInterface {

  object MoveJson {
    // Step 2: Define implicit Reads and Writes for Move
    implicit val moveWrites: Writes[Move] = new Writes[Move] {
      def writes(move: Move): JsValue = Json.obj(
        "name" -> move.name,
        "power" -> move.power,
        "moveType" -> move.moveType,
        "statusEffect" -> move.statusEffect
      )
    }

    implicit val moveReads: Reads[Move] = new Reads[Move] {
      def reads(json: JsValue): JsResult[Move] = {
        for {
          name <- (json \ "name").validate[String]
          power <- (json \ "power").validate[Int]
          moveType <- (json \ "moveType").validate[String]
          statusEffect <- (json \ "statusEffect").validate[String]
        } yield Move(name, power, moveType, statusEffect)
      }
    }

    object ChoiceJson {

      // Step 1: Implicit Writes for each choice type
      implicit val attackChoiceWrites: Writes[AttackChoice] = (
        (JsPath \ "move").writeNullable[Move]
      )(unlift(AttackChoice.unapply))

      implicit val itemChoiceWrites: Writes[ItemChoice] = (
        (JsPath \ "item").write[Item]
      )(unlift(ItemChoice.unapply))

      implicit val switchPokemonChoiceWrites: Writes[SwitchPokemonChoice] = (
        (JsPath \ "pokemon").writeNullable[Pokemon]
      )(unlift(SwitchPokemonChoice.unapply))

      // Step 2: Implicit Reads for each choice type
      implicit val attackChoiceReads: Reads[AttackChoice] = (
        (JsPath \ "move").readNullable[Move]
      )(AttackChoice.apply _)

      implicit val itemChoiceReads: Reads[ItemChoice] = (
        (JsPath \ "item").read[Item]
      )(ItemChoice.apply _)

      implicit val switchPokemonChoiceReads: Reads[SwitchPokemonChoice] = (
        (JsPath \ "pokemon").readNullable[Pokemon]
      )(SwitchPokemonChoice.apply _)
    }

    object PokemonJson {

      // Step 1: Implicit Writes for Pokemon
      implicit val pokemonWrites: Writes[Pokemon] = (
        (JsPath \ "id").write[Int] and
          (JsPath \ "name").write[String] and
          (JsPath \ "hp").write[Int] and
          (JsPath \ "moves").write[Seq[Move]] and
          (JsPath \ "speed").write[Int] and
          (JsPath \ "pokeType").write[String] and
          (JsPath \ "currentMove").write[Move]
      )(unlift(Pokemon.unapply))

      // Step 2: Implicit Reads for Pokemon
      implicit val pokemonReads: Reads[Pokemon] = (
        (JsPath \ "id").read[Int] and
          (JsPath \ "name").read[String] and
          (JsPath \ "hp").read[Int] and
          (JsPath \ "moves").read[Seq[Move]] and
          (JsPath \ "speed").read[Int] and
          (JsPath \ "pokeType").read[String] and
          (JsPath \ "currentMove").read[Move]
      )(Pokemon.apply _)

    }

    import play.api.libs.json._
import play.api.libs.functional.syntax._

object TrainerJson {

  // Step 1: Implicit Writes for Trainer
  implicit val trainerWrites: Writes[Trainer] = (
    (JsPath \ "pokemons").write[Vector[Pokemon]] and
    (JsPath \ "currentPokemon").write[Pokemon] and
    (JsPath \ "choice").writeNullable[Choice]
  )(unlift(Trainer.unapply))

  // Step 2: Implicit Reads for Trainer
  implicit val trainerReads: Reads[Trainer] = (
    (JsPath \ "pokemons").read[Vector[Pokemon]] and
    (JsPath \ "currentPokemon").read[Pokemon] and
    (JsPath \ "choice").readNullable[Choice]
  )(Trainer.apply _)

}




  }

}

 */
