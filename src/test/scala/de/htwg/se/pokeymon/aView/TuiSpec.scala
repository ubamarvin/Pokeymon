import de.htwg.se.Pokeymon.aView.Tui
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyFlatSpec with Matchers {

  "Tui" should "process 'q' command correctly" in {
    val tui = new Tui(null) // Null als Dummy-Controller übergeben

    var output = ""
    Console.withOut(scala.Console.out) {
      tui.processInput("q")
    }

    output.trim should be ("Game quitted")
  }

  it should "process 'test' command correctly" in {
    val tui = new Tui(null) // Null als Dummy-Controller übergeben

    var output = ""
    Console.withOut(scala.Console.out) {
      tui.processInput("test")
    }

    output.trim should be ("Unknown command")
  }
}
