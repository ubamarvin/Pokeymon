package de.htwg.se.Pokeymon.Model.FileIo.FileIoJson
import scala.io.Source
import java.io.File

import play.api.libs.json._

import com.google.inject.{Inject, Guice}
import de.htwg.se.Pokeymon.PokeymonModule
import de.htwg.se.Pokeymon.Model.GameComponent._

import de.htwg.se.Pokeymon.Model._
import de.htwg.se.Pokeymon.Model.FileIo.FileIOInterface
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.GameComponent.Game._
import de.htwg.se.Pokeymon.Model.GameComponent.StatusEffectStrategyContext

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
import de.htwg.se.Pokeymon.Model.GameComponent.{ParalyzedState, SleepState, PoisonedState, BurnedState, NormalState}

class FileIoJson extends FileIOInterface {

  def save(game: GameInterface): Unit =
    saveGame(game)
    println("GameStateSaved")

  private def saveGame(game: GameInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    val jsonString = Json.prettyPrint(gameToJson(game))
    pw.write(jsonString)
    pw.close()
    println(jsonString)
  }

  // _______________

  def moveToJson(move: Move): JsValue = {
    Json.obj(
      "name" -> move.name,
      "power" -> move.power,
      "moveType" -> move.moveType,
      "statusEffect" -> move.statusEffect
    )
  }

  // ___status to json

// Method to convert PoisonedState to JSON
  def poisonedStateToJson(status: PoisonedState): JsValue = {
    Json.obj(
      "stateType" -> "PoisonedState",
      "name" -> status.statusName,
      "duration" -> status.duration,
      "intensity" -> status.intensity
    )
  }

  // Method to convert BurnedState to JSON
  def burnedStateToJson(status: BurnedState): JsValue = {
    Json.obj(
      "stateType" -> "BurnedState",
      "name" -> status.statusName,
      "duration" -> status.duration,
      "intensity" -> status.intensity
    )
  }

  // Method to convert SleepState to JSON
  def sleepStateToJson(status: SleepState): JsValue = {
    Json.obj(
      "stateType" -> "SleepState",
      "name" -> status.statusName,
      "duration" -> status.duration
    )
  }

  // Method to convert ParalyzedState to JSON
  def paralyzedStateToJson(status: ParalyzedState): JsValue = {
    Json.obj(
      "stateType" -> "ParalyzedState",
      "name" -> status.statusName,
      "duration" -> status.duration
    )
  }

  // Method to convert StatusEffectStrategyContext to JSON based on its concrete type
  def statusToJson(status: StatusEffectStrategyContext): JsValue = {
    status.strategy match {
      case poisoned: PoisonedState   => poisonedStateToJson(poisoned)
      case burned: BurnedState       => burnedStateToJson(burned)
      case sleep: SleepState         => sleepStateToJson(sleep)
      case paralyzed: ParalyzedState => paralyzedStateToJson(paralyzed)
      case normal: NormalState       => Json.obj("stateType" -> "NormalState") // Example, adjust as needed
      case _                         => throw new IllegalArgumentException("Unsupported status type")
    }
  }

  def pokemonToJson(pk: Pokemon): JsValue = {
    Json.obj(
      "id" -> pk.id,
      "name" -> pk.name,
      "hp" -> pk.hp,
      "moves" -> pk.moves.map(moveToJson),
      "speed" -> pk.speed,
      "pokeType" -> pk.pokeType,
      "currentMove" -> moveToJson(pk.currentMove),
      "status" -> statusToJson(pk.status)
    )
  }

  def attackChoiceToJson(choice: AttackChoice): JsValue = {
    Json.obj(
      "attackChoice" -> choice.move.map(moveToJson).getOrElse(Json.obj())
    )
  }

  def itemChoiceToJson(choice: ItemChoice): JsValue = {
    Json.obj(
      "itemChoice" -> choice.item.name
    )
  }

  def switchPokemonChoiceToJson(choice: SwitchPokemonChoice): JsValue = {
    choice.pokemon match {
      case Some(pokemon) => Json.obj("switchPokemonChoice" -> pokemonToJson(pokemon))

      case None => Json.obj("switchPokemonChoice" -> JsNull)
    }
  }

  private def choiceToJson(choice: Option[Choice]): JsValue = {
    choice match {
      case Some(attackChoice: AttackChoice)        => attackChoiceToJson(attackChoice)
      case Some(itemChoice: ItemChoice)            => itemChoiceToJson(itemChoice)
      case Some(switchChoice: SwitchPokemonChoice) => switchPokemonChoiceToJson(switchChoice)
      case None                                    => Json.obj()
    }
  }

  def trainerToJson(trainer: Trainer): JsValue = {
    Json.obj(
      "pokemons" -> trainer.pokemons.map(pokemonToJson),
      "currentPokemon" -> pokemonToJson(trainer.currentPokemon),
      "choice" -> choiceToJson(trainer.choice)
    )
  }

  def pokedexToJson(pokedex: Pokedex): JsValue = {
    Json.obj(
      "pokedex" -> Json.obj(
        "available_pokemon" -> pokedex.available_pokemon.map(pokemonToJson)
      )
    )
  }

  // building the gameState Json Block

  def gameStateToJson(gameState: GameState) = {

    gameState match {
      case deadState: YourDeadState         => yourDeadStateToJson(deadState)
      case pickState: PickPokemonState      => pickPokemonStateToJson(pickState)
      case mainState: MainState             => mainStateToJson(mainState)
      case battleEvalState: BattleEvalState => battleEvalStateToJson(battleEvalState)
      case attackState: ChooseAttackState   => chooseAttackStateToJson(attackState)
      case itemState: ChooseItemState       => chooseItemStateToJson(itemState)
      case switchState: SwitchPokemonState  => switchPokemonStateToJson(switchState)
      case _                                => Json.obj() //
    }
  }

  def yourDeadStateToJson(state: YourDeadState): JsValue = {
    Json.obj(
      "type" -> "YourDeadState",
      "winner" -> (if (state.player.hasNoPokemonleft()) "Opponent has" else "You have"),
      "roundReport" -> state.roundReport,
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent)
    )
  }

  def pickPokemonStateToJson(state: PickPokemonState): JsValue = {
    Json.obj(
      "type" -> "PickPokemonState",
      "player" -> trainerToJson(state.player),
      "pokedex" -> pokedexToJson(state.pokedex),
      "picks" -> state.picks,
      "opponent" -> trainerToJson(state.opponent)
    )
  }

// Method to convert MainState to JSON
  def mainStateToJson(state: MainState): JsValue = {
    Json.obj(
      "type" -> "MainState",
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent),
      "roundReport" -> state.roundReport
    )
  }

// Method to convert BattleEvalState to JSON
  def battleEvalStateToJson(state: BattleEvalState): JsValue = {
    Json.obj(
      "type" -> "BattleEvalState",
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent)
    )
  }

// Method to convert ChooseAttackState to JSON
  def chooseAttackStateToJson(state: ChooseAttackState): JsValue = {
    Json.obj(
      "type" -> "ChooseAttackState",
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent)
    )
  }

// Method to convert ChooseItemState to JSON
  def chooseItemStateToJson(state: ChooseItemState): JsValue = {
    Json.obj(
      "type" -> "ChooseItemState",
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent)
    )
  }

// Method to convert SwitchPokemonState to JSON
  def switchPokemonStateToJson(state: SwitchPokemonState): JsValue = {
    Json.obj(
      "type" -> "SwitchPokemonState",
      "player" -> trainerToJson(state.player),
      "opponent" -> trainerToJson(state.opponent)
    )
  }

  def gameToJson(game: GameInterface): JsValue = {
    Json.obj(
      "state" -> gameStateToJson(game.state),
      "undoStack" -> Json.toJson(game.undoStack.map(gameStateToJson)),
      "redoStack" -> Json.toJson(game.redoStack.map(gameStateToJson))
    )
  }

///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________
///_____________________LOAD____________________

  def load: GameInterface = {
    var game: GameInterface = null
    val fileContent = Source.fromFile("game.json").getLines.mkString
    val json = Json.parse(fileContent)
    game = jsonToGame(json)
    game
  }
  def jsonToMove(json: JsValue): Move = {
    Move(
      (json \ "name").as[String],
      (json \ "power").as[Int],
      (json \ "moveType").as[String],
      (json \ "statusEffect").as[String]
    )
  }

  // Method to convert JSON to PoisonedState
  def jsonToPoisonedState(json: JsValue): PoisonedState = {
    PoisonedState(
      (json \ "duration").as[Int],
      (json \ "intensity").as[Int]
    )
  }

  // Method to convert JSON to BurnedState
  def jsonToBurnedState(json: JsValue): BurnedState = {
    BurnedState(
      (json \ "duration").as[Int],
      (json \ "intensity").as[Int]
    )
  }

  // Method to convert JSON to SleepState
  def jsonToSleepState(json: JsValue): SleepState = {
    SleepState(
      (json \ "duration").as[Int]
    )
  }

  // Method to convert JSON to ParalyzedState
  def jsonToParalyzedState(json: JsValue): ParalyzedState = {
    ParalyzedState(
      (json \ "duration").as[Int]
    )
  }

  // Method to convert JSON to StatusEffectStrategyContext based on stateType field
  def jsonToStatus(json: JsValue): StatusEffectStrategyContext = {
    val stateType = (json \ "stateType").as[String]
    stateType match {
      case "PoisonedState"  => StatusEffectStrategyContext(jsonToPoisonedState(json))
      case "BurnedState"    => StatusEffectStrategyContext(jsonToBurnedState(json))
      case "SleepState"     => StatusEffectStrategyContext(jsonToSleepState(json))
      case "ParalyzedState" => StatusEffectStrategyContext(jsonToParalyzedState(json))
      case "NormalState"    => StatusEffectStrategyContext(new NormalState()) // Example, adjust as needed
      case _                => throw new IllegalArgumentException(s"Unsupported stateType: $stateType")
    }
  }

  def jsonToPokemon(json: JsValue): Pokemon = {
    Pokemon(
      (json \ "id").as[Int],
      (json \ "name").as[String],
      (json \ "hp").as[Int],
      (json \ "moves").as[List[JsValue]].map(jsonToMove),
      (json \ "speed").as[Int],
      (json \ "pokeType").as[String],
      jsonToMove((json \ "currentMove").as[JsValue]),
      jsonToStatus((json \ "status").as[JsValue])
    )
  }

// Converting JSON to AttackChoice
  def jsonToAttackChoice(json: JsValue): AttackChoice = {
    AttackChoice(
      (json \ "attackChoice").asOpt[JsValue].map(jsonToMove)
    )
  }

// Converting JSON to ItemChoice
  def jsonToItemChoice(json: JsValue): ItemChoice = {
    ItemChoice(
      Item((json \ "itemChoice").as[String]),
      null
    )
  }

// Converting JSON to SwitchPokemonChoice
  def jsonToSwitchPokemonChoice(json: JsValue): SwitchPokemonChoice = {
    SwitchPokemonChoice(
      (json \ "switchPokemonChoice").asOpt[JsValue].map(jsonToPokemon)
    )
  }

// Converting JSON to Choice
  private def jsonToChoice(json: JsValue): Option[Choice] = {
    (json \ "attackChoice").asOpt[JsValue].map(jsonToAttackChoice) orElse
      (json \ "itemChoice").asOpt[JsValue].map(jsonToItemChoice) orElse
      (json \ "switchPokemonChoice").asOpt[JsValue].map(jsonToSwitchPokemonChoice)
  }

// Converting JSON to Trainer
  def jsonToTrainer(json: JsValue): Trainer = {
    Trainer(
      (json \ "pokemons").as[Vector[JsValue]].map(jsonToPokemon),
      jsonToPokemon((json \ "currentPokemon").as[JsValue]),
      jsonToChoice((json \ "choice").as[JsValue])
    )
  }

// Converting JSON to Pokedex
  def jsonToPokedex(json: JsValue): Pokedex = {
    Pokedex(
      (json \ "pokedex" \ "available_pokemon").as[Vector[JsValue]].map(jsonToPokemon)
    )
  }

// Converting JSON to YourDeadState
  def jsonToYourDeadState(json: JsValue): YourDeadState = {
    val roundReport = (json \ "roundReport").as[String]
    val player = jsonToTrainer((json \ "player").as[JsValue])
    val opponent = jsonToTrainer((json \ "opponent").as[JsValue])

    YourDeadState(player, opponent, roundReport)
  }

// Converting JSON to PickPokemonState
  def jsonToPickPokemonState(json: JsValue): PickPokemonState = {
    PickPokemonState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToPokedex((json \ "pokedex").as[JsValue]),
      (json \ "picks").as[Int],
      jsonToTrainer((json \ "opponent").as[JsValue])
    )
  }

// Converting JSON to MainState
  def jsonToMainState(json: JsValue): MainState = {
    MainState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToTrainer((json \ "opponent").as[JsValue]),
      (json \ "roundReport").as[String]
    )
  }

// Converting JSON to BattleEvalState
  def jsonToBattleEvalState(json: JsValue): BattleEvalState = {
    BattleEvalState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToTrainer((json \ "opponent").as[JsValue])
    )
  }

// Converting JSON to ChooseAttackState
  def jsonToChooseAttackState(json: JsValue): ChooseAttackState = {
    ChooseAttackState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToTrainer((json \ "opponent").as[JsValue])
    )
  }

// Converting JSON to ChooseItemState
  def jsonToChooseItemState(json: JsValue): ChooseItemState = {
    ChooseItemState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToTrainer((json \ "opponent").as[JsValue])
    )
  }

// Converting JSON to SwitchPokemonState
  def jsonToSwitchPokemonState(json: JsValue): SwitchPokemonState = {
    SwitchPokemonState(
      jsonToTrainer((json \ "player").as[JsValue]),
      jsonToTrainer((json \ "opponent").as[JsValue])
    )
  }

// Converting JSON to GameState
  def jsonToGameState(json: JsValue): GameState = {
    (json \ "type").as[String] match {
      case "YourDeadState"      => jsonToYourDeadState(json)
      case "PickPokemonState"   => jsonToPickPokemonState(json)
      case "MainState"          => jsonToMainState(json)
      case "BattleEvalState"    => jsonToBattleEvalState(json)
      case "ChooseAttackState"  => jsonToChooseAttackState(json)
      case "ChooseItemState"    => jsonToChooseItemState(json)
      case "SwitchPokemonState" => jsonToSwitchPokemonState(json)
      case _                    => throw new IllegalArgumentException("Unknown state type")
    }
  }

// Converting JSON to Game
  def jsonToGame(json: JsValue): GameInterface = {

    val state = jsonToGameState((json \ "state").as[JsValue])
    val undoStack = (json \ "undoStack").as[Vector[JsValue]].map(jsonToGameState)
    val redoStack = (json \ "redoStack").as[Vector[JsValue]].map(jsonToGameState)

    new Game(state, undoStack, redoStack)

  }

}
