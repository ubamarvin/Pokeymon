import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Pokeymon.aView.Gui.Scenes.AttackScene
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.Model.GameData._
import scalafx.application.Platform
import scalafx.scene.control.Button

class AttackSceneSpec extends AnyWordSpec with Matchers {

  "AttackScene" should {
    "handle button actions correctly" in {
      // Initialize the JavaFX toolkit
      Platform.startup(() => {})


      // Simulate button action
      // Verify that the corresponding handleInput method was called with "back"

    }
  }
}
