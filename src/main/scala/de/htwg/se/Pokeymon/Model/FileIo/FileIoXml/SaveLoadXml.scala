package de.htwg.se.Pokeymon.Model.FileIo.FileIoXml

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

import scala.xml._

import scala.xml.{NodeSeq, PrettyPrinter}

class SaveLoadXml extends FileIOInterface {

  def save(game: GameInterface): Unit =
    saveGame(game)
    println("GameStateSaved")

  private def saveGame(game: GameInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xmlString = prettyPrinter.format(gameToXml(game))
    pw.write(xmlString)
    pw.close()
    println(xmlString)
  }

// Building the Trainer to xml block
  def moveToXml(move: Move): Elem = {
    <move>
    <name>{move.name}</name>
    <power>{move.power}</power>
    <moveType>{move.moveType}</moveType>
    <statusEffect>{move.statusEffect}</statusEffect>
  </move>
  }

  def pokemonToXml(pk: Pokemon): Elem = {
    <pokemon>
        <id>{pk.id}</id>
        <name>{pk.name}</name>
        <hp>{pk.hp}</hp>
        <moves>
          {pk.moves.map(moveToXml)}
        </moves>
        <speed>{pk.speed}</speed>
        <pokeType>{pk.pokeType}</pokeType>
        <currentMove>{moveToXml(pk.currentMove)}</currentMove>
  </pokemon>
  }

  def attackChoiceToXml(choice: AttackChoice): Elem = {
    <AttackChoice>
    {choice.move.map(moveToXml).getOrElse(NodeSeq.Empty)}
  </AttackChoice>
  }

  def itemChoiceToXml(choice: ItemChoice): Elem = {
    <ItemChoice>
    <item>{choice.item.name}</item>

  </ItemChoice>
  }

  def switchPokemonChoiceToXml(choice: SwitchPokemonChoice): Elem = {
    <SwitchPokemonChoice>
    {
      choice.pokemon match {
        case Some(pokemon) => pokemonToXml(pokemon)
        case None          => NodeSeq.Empty
      }
    }
  </SwitchPokemonChoice>
  }

  def trainerToXml(trainer: Trainer): Elem = {
    <Trainer>
      <pokemons>
        {trainer.pokemons.map(pokemonToXml)}
      </pokemons>
      <currentPokemon>
        {pokemonToXml(trainer.currentPokemon)}
      </currentPokemon>
      {choiceToXml(trainer.choice)}
    </Trainer>
  }

  private def choiceToXml(choice: Option[Choice]): NodeSeq = {
    choice match {
      case Some(attackChoice: AttackChoice)        => attackChoiceToXml(attackChoice)
      case Some(itemChoice: ItemChoice)            => itemChoiceToXml(itemChoice)
      case Some(switchChoice: SwitchPokemonChoice) => switchPokemonChoiceToXml(switchChoice)
      case None                                    => NodeSeq.Empty // Handle the case where choice is None
    }
  }

  def pokedexToXml(pokedex: Pokedex): Elem = {
    <Pokedex>
      <available_pokemon>
        {pokedex.available_pokemon.map(pokemonToXml)}
      </available_pokemon>
    </Pokedex>
  }

  // building the gameState to xml block
  def gameStateToXml(gameState: GameState): Elem = {
    gameState match {
      case deadState: YourDeadState         => yourDeadStateToXml(deadState)
      case pickState: PickPokemonState      => pickPokemonStateToXml(pickState)
      case mainState: MainState             => mainStateToXml(mainState)
      case battleEvalState: BattleEvalState => battleEvalStateToXml(battleEvalState)
      case attackState: ChooseAttackState   => chooseAttackStateToXml(attackState)
      case itemState: ChooseItemState       => chooseItemStateToXml(itemState)
      case switchState: SwitchPokemonState  => switchPokemonStateToXml(switchState)
      case _                                => <GameState/> // Handle unknown or future states
    }
  }

  // Method to convert YourDeadState to XML
  def yourDeadStateToXml(state: YourDeadState): Elem = {
    <YourDeadState type="YourDeadState">
      <winner>{if (state.player.hasNoPokemonleft()) "Opponent has" else "You have"}</winner>
      <roundReport>{state.roundReport}</roundReport>
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </YourDeadState>
  }

  // Method to convert PickPokemonState to XML
  def pickPokemonStateToXml(state: PickPokemonState): Elem = {
    <PickPokemonState type="PickPokemonState">
      <player>{trainerToXml(state.player)}</player>
      <pokedex>{pokedexToXml(state.pokedex)}</pokedex>
      <picks>{state.picks}</picks>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </PickPokemonState>
  }

  // Method to convert MainState to XML
  def mainStateToXml(state: MainState): Elem = {
    <MainState type="MainState">
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
      <roundReport>{state.roundReport}</roundReport>
    </MainState>
  }

  // Method to convert BattleEvalState to XML
  def battleEvalStateToXml(state: BattleEvalState): Elem = {
    <BattleEvalState type="BattleEvalState">
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </BattleEvalState>
  }

  // Method to convert ChooseAttackState to XML
  def chooseAttackStateToXml(state: ChooseAttackState): Elem = {
    <ChooseAttackState type="ChooseAttackState">
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </ChooseAttackState>
  }

  // Method to convert ChooseItemState to XML
  def chooseItemStateToXml(state: ChooseItemState): Elem = {
    <ChooseItemState type="ChooseItemState">
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </ChooseItemState>
  }

  // Method to convert SwitchPokemonState to XML
  def switchPokemonStateToXml(state: SwitchPokemonState): Elem = {
    <SwitchPokemonState type="SwitchPokemonState">
      <player>{trainerToXml(state.player)}</player>
      <opponent>{trainerToXml(state.opponent)}</opponent>
    </SwitchPokemonState>
  }

  def gameToXml(game: GameInterface): Elem = {
    <Game>
      <state>{gameStateToXml(game.state)}</state>
      <undoStack>
        {game.undoStack.map(gameStateToXml)}
      </undoStack>
      <redoStack>
        {game.redoStack.map(gameStateToXml)}
      </redoStack>
    </Game>
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

  def load: GameInterface =
    var game: GameInterface = null
    val file = scala.xml.XML.loadFile("game.xml")
    game = GameLoader.gameFromXml(file)
    game

  object MoveLoader {

    def loadMove(xml: Node): Move = {
      Move(
        (xml \\ "name").text,
        (xml \\ "power").text.toInt,
        (xml \\ "moveType").text,
        (xml \\ "statusEffect").text
      )
    }

    def loadMoves(xml: NodeSeq): Seq[Move] = {
      xml.map(loadMove)
    }

  }

  object PokemonLoader {

    def loadPokemon(xml: Node): Pokemon = {
      Pokemon(
        (xml \\ "id").text.toInt,
        (xml \ "name").text,
        (xml \\ "hp").text.toInt,
        MoveLoader.loadMoves(xml \\ "moves" \ "move").toList,
        (xml \\ "speed").text.toInt,
        (xml \\ "pokeType").text,
        MoveLoader.loadMove((xml \\ "currentMove").head)
      )
    }

    def loadPokemons(xml: NodeSeq): Vector[Pokemon] = {
      xml.map(loadPokemon).toVector
    }

  }

  object AttackChoiceLoader {

    def loadAttackChoice(xml: Node): AttackChoice = {
      val moveXml = (xml \\ "move").headOption.getOrElse(throw new IllegalArgumentException("AttackChoice missing move"))
      val move = if (moveXml.isEmpty) None else Some(MoveLoader.loadMove(moveXml))
      AttackChoice(move)
    }

  }

  object ItemChoiceLoader {

    def loadItemChoice(xml: Node): Unit = {
      val itemName = (xml \ "item").text
      val item = Item(itemName) // Assuming Item has a constructor that takes a name

    }

  }

  object SwitchPokemonChoiceLoader {

    def loadSwitchPokemonChoice(xml: Node): SwitchPokemonChoice = {
      val pokemonOpt = (xml \\ "pokemon").headOption.map(PokemonLoader.loadPokemon)
      val pokemon = if (pokemonOpt.isDefined) Some(pokemonOpt.get) else None
      SwitchPokemonChoice(pokemon)
    }

  }

  import scala.xml.{Elem, NodeSeq}

  object TrainerLoader {

    def trainerFromXml(xml: NodeSeq): Trainer = {
      println(s"XML received for trainer:\n$xml")

      // Extracting the first element assuming there's only one trainer element
      val trainerXml = xml.headOption.getOrElse(throw new IllegalArgumentException("Trainer XML not found"))
      val pokemons = (trainerXml \\ "pokemons" \ "pokemon").map(PokemonLoader.loadPokemon).toVector
      val currentPokemon = PokemonLoader.loadPokemon((trainerXml \\ "currentPokemon" \ "pokemon").head)
      val choice = (trainerXml \\ "choice").headOption.map(choiceFromXml)

      Trainer(pokemons, currentPokemon, choice)
    }

    private def choiceFromXml(xml: NodeSeq): Choice = {
      (xml \\ "AttackChoice").headOption.map(attackChoiceFromXml).getOrElse {
        (xml \\ "ItemChoice").headOption.map(itemChoiceFromXml).getOrElse {
          (xml \\ "SwitchPokemonChoice").headOption.map(switchPokemonChoiceFromXml).getOrElse {
            throw new IllegalArgumentException("Unknown choice type in XML")
          }
        }
      }
    }

    private def attackChoiceFromXml(xml: NodeSeq): AttackChoice = {
      val moveXml = (xml \\ "move").headOption.getOrElse(throw new IllegalArgumentException("AttackChoice missing move"))
      val move = MoveLoader.loadMove(moveXml)
      AttackChoice(Some(move))
    }

    private def itemChoiceFromXml(xml: NodeSeq): ItemChoice = {
      val itemName = (xml \\ "item").headOption.map(_.text).getOrElse(throw new IllegalArgumentException("ItemChoice missing item"))
      val item = Item(itemName) // Assuming there's a way to construct Item from its name
      ItemChoice(item, null) // Replace null with the appropriate targetPokemon if available in XML
    }

    private def switchPokemonChoiceFromXml(xml: NodeSeq): SwitchPokemonChoice = {
      val pokemonOpt = (xml \\ "pokemon").headOption.map(PokemonLoader.loadPokemon)
      val pokemon = if (pokemonOpt.isDefined) Some(pokemonOpt.get) else None
      SwitchPokemonChoice(pokemon)
    }

  }

  object PokedexLoader {
    def loadPokedex(xml: Node): Pokedex = {
      val availablePokemon = (xml \\ "Pokedex" \ "available_pokemon" \ "pokemon").map(PokemonLoader.loadPokemon).toVector
      Pokedex(availablePokemon)
    }
  }

  /// ______________Game__________________

  object GameStateLoader {

    def gameStateFromXml(xml: Node): GameState = {
      println(xml)
      val stateType = (xml \\ "@type").text.trim // Extract type attribute and store it in a variable
      println(s"Extracted type attribute value: $stateType") // Print the extracted value for debugging
      (xml \\ "@type").text match {
        case "YourDeadState"      => yourDeadStateFromXml(xml)
        case "PickPokemonState"   => pickPokemonStateFromXml(xml)
        case "MainState"          => mainStateFromXml(xml)
        case "BattleEvalState"    => battleEvalStateFromXml(xml)
        case "ChooseAttackState"  => chooseAttackStateFromXml(xml)
        case "ChooseItemState"    => chooseItemStateFromXml(xml)
        case "SwitchPokemonState" => switchPokemonStateFromXml(xml)
        case _                    => throw new IllegalArgumentException("Unknown game state type in XML")
      }
    }

    private def yourDeadStateFromXml(xml: Node): YourDeadState = {
      val winner = (xml \\ "winner").text
      val roundReport = (xml \\ "roundReport").text
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      YourDeadState(player, opponent, roundReport)
    }

    def pickPokemonStateFromXml(xml: Node): PickPokemonState = {
      println("pickpokexml: " + xml)
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val pokedexNode = (xml \\ "pokedex" \ "Pokedex").headOption.getOrElse(throw new IllegalArgumentException("Pokedex XML not found"))
      val pokedex = PokedexLoader.loadPokedex(pokedexNode)
      val picks = (xml \\ "picks").text.toInt
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      PickPokemonState(player, pokedex, picks, opponent)
    }
    private def mainStateFromXml(xml: Node): MainState = {
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      val roundReport = (xml \\ "roundReport").text
      MainState(player, opponent, roundReport)
    }

    private def battleEvalStateFromXml(xml: Node): BattleEvalState = {
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      BattleEvalState(player, opponent)
    }

    private def chooseAttackStateFromXml(xml: Node): ChooseAttackState = {
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      ChooseAttackState(player, opponent)
    }

    private def chooseItemStateFromXml(xml: Node): ChooseItemState = {
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      ChooseItemState(player, opponent)
    }

    private def switchPokemonStateFromXml(xml: Node): SwitchPokemonState = {
      val player = TrainerLoader.trainerFromXml(xml \\ "player" \ "Trainer")
      val opponent = TrainerLoader.trainerFromXml(xml \\ "opponent" \ "Trainer")
      SwitchPokemonState(player, opponent)
    }

  }

  object GameLoader {
    import com.google.inject.{Inject, Guice}
    import de.htwg.se.Pokeymon.PokeymonModule
    import de.htwg.se.Pokeymon.Model.GameComponent._

    def gameFromXml(xml: Node): GameInterface = {
      var state = GameStateLoader.gameStateFromXml((xml \ "state").head)
      val undoStack = (xml \ "undoStack" \ "_").map(GameStateLoader.gameStateFromXml).toVector
      val redoStack = (xml \ "redoStack" \ "_").map(GameStateLoader.gameStateFromXml).toVector

      val injector = Guice.createInjector(new PokeymonModule)
      val game = injector.getInstance(classOf[GameInterface]).asInstanceOf[Game]

      new Game(state, undoStack, redoStack)
    }

  }

} //end of class