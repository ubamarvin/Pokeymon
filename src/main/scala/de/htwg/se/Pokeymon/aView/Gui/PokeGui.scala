package de.htwg.se.Pokeymon.aView.Gui

import de.htwg.se.Pokeymon.aView.Gui.Scenes.{BattleScene, PickPokeScene, BaseScene, MainScene, AttackScene, ItemScene, SwitchScene, DeadScene, OpeningScene}
import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.Controller

import scalafx.application.JFXApp3
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{HBox, StackPane, VBox, Priority, GridPane, Region}
import scalafx.scene.paint.Color
import scalafx.scene.control.{Button, TextField}
import scalafx.geometry.Pos
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scala.compiletime.uninitialized
import scalafx.application.Platform
import com.sun.javafx.application.PlatformImpl

import de.htwg.se.Pokeymon.Model._

//Mirrors ContextClass
case class PokeGui(controller: Controller) extends JFXApp3 with Observer {
  controller.add(this)
  PlatformImpl.startup(() => {})

  override def update: Unit =
    Platform.runLater {
      if (stage != null) {

        println("Gui.update")
        val content = controller.getSceneContent()

        content.state match {
          case "pick" =>
            println("pick state")
            stage.setScene(new PickPokeScene(controller))
          case "attack" =>
            println("attack state")
            stage.setScene(new AttackScene(controller))
          case "main" =>
            println("main state")
            stage.setScene(new MainScene(controller))
          case "battle" =>
            println("battle state")
            stage.setScene(new BattleScene(controller))
          case "switch" =>
            println("switch state")
            stage.setScene(new SwitchScene(controller))
          case "item" =>
            println("item state")
            stage.setScene(new ItemScene(controller))
          case "dead" =>
            println("dead state")
            stage.setScene(new DeadScene(controller))

          case _ => println("unknown state")
        }
      } else { println("stage is null ") }
    }

  override def start(): Unit = {

    // set stage
    stage = new JFXApp3.PrimaryStage {
      title = "Pokemon"
      scene = new OpeningScene(controller) //

    }

  }
}
