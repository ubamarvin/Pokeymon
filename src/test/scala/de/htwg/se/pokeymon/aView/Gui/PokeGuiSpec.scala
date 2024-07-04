import org.scalatest._
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface
import de.htwg.se.Pokeymon.aView.Gui.PokeGui
import org.scalatest.flatspec.AnyFlatSpec
import de.htwg.se.Pokeymon.Util.Observer
import org.scalatest.matchers.should.Matchers.convertToStringShouldWrapperForVerb

class PokeGuiSpec extends AnyFlatSpec {

  // Dummy ControllerInterface for testing

  "PokeGui" should "start without errors" in {
    assertThrows[Exception] {
    }
  }
}
