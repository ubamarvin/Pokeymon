package de.htwg.se.Pokeymon.Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scalafx.application.Platform
import scalafx.scene.Scene
import de.htwg.se.Pokeymon.Controller.Controller
import de.htwg.se.Pokeymon.Model.Game
import org.scalamock.scalatest.MockFactory
import de.htwg.se.Pokeymon.aView.Gui.PokeGui // Beispiel-Import für PokeGui (anpassen, wenn nötig)
import de.htwg.se.Pokeymon.aView.Gui.Scenes.SceneContent // Beispiel-Import für SceneContent (anpassen, wenn nötig)

class PokeGuiSpec extends AnyWordSpec with Matchers with MockFactory {

  "A PokeGui" when {

    "updated with different controller states" should {

      "set PickPokeScene when controller state is 'pick'" in {
        val mockGame = mock[Game] // Mocking Game dependency
        val mockController = new Controller(mockGame)
        val gui = PokeGui(mockController)

        Platform.runLater {
          mockController.setSceneContent(new SceneContent("pick")) // Beispiel für die Verwendung von SceneContent (anpassen, wenn nötig)
        }
        
        Thread.sleep(1000) // Warten, bis Platform.runLater ausgeführt wird

        gui.update()

        gui.stage.scene.value shouldBe a [de.htwg.se.Pokeymon.aView.Gui.Scenes.PickPokeScene] // Beispiel-Assertion (anpassen, wenn nötig)
      }

      "set AttackScene when controller state is 'attack'" in {
        val mockGame = mock[Game] // Mocking Game dependency
        val mockController = new Controller(mockGame)
        val gui = PokeGui(mockController)

        Platform.runLater {
          mockController.setSceneContent(new SceneContent("attack")) // Beispiel für die Verwendung von SceneContent (anpassen, wenn nötig)
        }

        Thread.sleep(1000) // Warten, bis Platform.runLater ausgeführt wird

        gui.update()

        gui.stage.scene.value shouldBe a [de.htwg.se.Pokeymon.aView.Gui.Scenes.AttackScene] // Beispiel-Assertion (anpassen, wenn nötig)
      }

      // Weitere Tests für andere Zustände wie 'main', 'battle', 'switch', 'item', 'dead' hinzufügen, wie benötigt
    }
  }
}
