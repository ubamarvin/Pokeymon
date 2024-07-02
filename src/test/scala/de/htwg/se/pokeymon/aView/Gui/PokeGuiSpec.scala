package de.htwg.se.Pokeymon.aView.Gui

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.Platform
import scalafx.scene.Scene
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Model.GameComponent.Content
import de.htwg.se.Pokeymon.aView.Gui.Scenes._

class PokeGuiSpec extends AnyWordSpec with MockitoSugar {

  "A PokeGui" should {
    val controllerMock = mock[ControllerInterface]
    val pokeGui = PokeGui(controllerMock)
    
    "update the stage based on the controller state" in {
      val contentMock = mock[Content]
      when(controllerMock.getSceneContent).thenReturn(contentMock)

      Platform.runLater {
        // Test different states
        val states = Map(
          "pick" -> classOf[PickPokeScene],
          "attack" -> classOf[AttackScene],
          "main" -> classOf[MainScene],
          "battle" -> classOf[BattleScene],
          "switch" -> classOf[SwitchScene],
          "item" -> classOf[ItemScene],
          "dead" -> classOf[DeadScene]
        )

        for ((state, sceneClass) <- states) {
          when(contentMock.state).thenReturn(state)
          pokeGui.update
          Thread.sleep(100) // Give time for Platform.runLater to execute
          pokeGui.stage.scene.value.getClass should be(sceneClass)
        }

        // Test unknown state
        when(contentMock.state).thenReturn("unknown")
        pokeGui.update
        Thread.sleep(100)
        // No change expected for unknown state, ensure the last valid scene remains
        pokeGui.stage.scene.value.getClass should be(classOf[DeadScene])
      }
    }

    "initialize the stage with the opening scene" in {
      pokeGui.start()
      pokeGui.stage.title.value should be("Pokemon")
      pokeGui.stage.scene.value.getClass should be(classOf[OpeningScene])
    }
  }
}
