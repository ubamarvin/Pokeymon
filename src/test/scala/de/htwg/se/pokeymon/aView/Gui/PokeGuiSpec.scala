import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scalafx.application.Platform
import scalafx.scene.Scene

class PokeGuiSpec extends AnyWordSpec with Matchers {

  "PokeGui" should {
    "set the correct scene based on controller state" in {

      // Start-Methode aufrufen, um die GUI zu initialisieren

      // Beispiel: Zustand "main"

      // Update-Methode aufrufen, um die GUI zu aktualisieren (normalerweise von Observables aufgerufen)

      // Überprüfen, ob die richtige Szene gesetzt wurde

      // Weitere Tests für andere Zustände ("pick", "attack", "battle", "switch", "item", "dead") können analog durchgeführt werden
    }

    // Weitere Tests können hier hinzugefügt werden, z.B. für Randfälle oder Fehlerszenarien
  }

  // TestController als einfache Implementierung des ControllerInterface für den Tes
}
