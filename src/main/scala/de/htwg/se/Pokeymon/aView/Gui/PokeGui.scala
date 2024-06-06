package de.htwg.se.Pokeymon.aView.Gui

import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.Controller

import scalafx.application.JFXApp3
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{HBox, StackPane, VBox, Priority}
import scalafx.scene.paint.Color
import scalafx.scene.control.{Button, TextField}
import scalafx.geometry.Pos
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scala.compiletime.uninitialized
import scalafx.application.Platform
import com.sun.javafx.application.PlatformImpl

//Mirrors ContextClass
case class PokeGui(controller: Controller) extends JFXApp3 with Observer {
  controller.add(this)
  PlatformImpl.startup(() => {})

  override def update: Unit =
    Platform.runLater {

      println("Gui.update")
      val content = controller.getSceneContent()
      content.state match {
        case "pick" =>
          println("pick state")
        // stage.setScene(new PickPokeScene(controller))
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

        case _ => println("unknown state")
      }
    }

  override def start(): Unit = {

    // set stage
    stage = new JFXApp3.PrimaryStage {
      title = "Pokemon"
      scene = new Scene(new StackPane(), 800, 600)
    }

  }
}

case class PickPokeScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("PickPoke")
  val button = new Button("Evoli Test") {
    onAction = () => {
      controller.handleInput("evoli")
    }
  }
  val button2 = new Button("done") {
    onAction = () => {
      controller.handleInput("d")
    }
  }

  root = new StackPane {
    children = List(text, button, button2)
  }

}

case class MainScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("MainScene")

  root = new StackPane {
    children = List(text)
  }

}

case class AttackScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("AttackScene")
  val button = new Button("Evoli Test kick") {
    onAction = () => {
      controller.handleInput("kick")
    }
  }

  root = new StackPane {
    children = List(text, button)
  }

}

case class SwitchScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("SwitchScene")

  root = new StackPane {
    children = List(text)
  }

}

case class BattleScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("BattleScene")

  root = new StackPane {
    children = List(text)
  }

}

case class ItemScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val text = new Text("ItemScene")

  root = new StackPane {
    children = List(text)
  }

}

abstract class BaseScene() extends Scene {
  // val backgroundImage: ImageView = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png") {

}
