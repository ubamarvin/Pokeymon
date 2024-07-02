package de.htwg.se.Pokeymon.Controller

import de.htwg.se.Pokeymon.Model.{Game, MainState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "handling input" should {
      "update the game state and notify observers" in {
        // Arrange
        val observerMock = new ObserverMock
      }
    }

    "switching Pokémon" should {
      "notify observers if the state is MainState" in {
        // Arrange
        val observerMock = new ObserverMock

        // Act

        // Assert
        observerMock.notified shouldBe true // Ensure observer notified
      }
    }

    "using an item" should {
      "notify observers if the state is mainState" in {
        // Arrange
        val observerMock = new ObserverMock

        // Assert
        observerMock.notified shouldBe true // Ensure observer notified
      }
    }

    "choosing a move" should {
      "notify observers if the state is MainBattleSt" in {
        // Arrange

        val observerMock = new ObserverMock



        // Assert
        observerMock.notified shouldBe true // Ensure observer notified
      }
    }
  }

  private class ObserverMock {
    var notified: Boolean = false
  }
}
