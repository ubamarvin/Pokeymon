package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData._

class GameSpec extends AnyFlatSpec with Matchers {

  // Ein Spielzustand, der für Tests verwendet wird
  case class TestState(content: Content) extends GameState {
    override def processInput(input: String): GameState = this
    override def gameToString(): String = "Test State"
    override def getContent(): Content = content
  }


  it should "handle input and update state" in {
    val pickState = PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent)
    val game = Game(pickState)

    val nextState = game.handleInput("some input")

    nextState.state should not be pickState
    nextState.undoStack.head shouldEqual pickState
  }




}
