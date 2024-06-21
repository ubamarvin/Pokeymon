package de.htwg.se.Pokeymon.aView.Gui.Scenes

import de.htwg.se.Pokeymon.aView.Gui.Scenes.BaseScene

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

case class DeadScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("Dead")

  val confirmButton = new Button("Yes") {
    onAction = () => {
      controller.handleInput("ja")
    }
  }

  val backButton = new Button("Back") {
    onAction = () => {
      controller.undo
    }
  }

  // Create an HBox to contain the buttons
  val buttonBox = new HBox {
    spacing = 20 // Spacing between buttons
    alignment = Pos.Center // Align buttons to the center horizontally
    children = List(confirmButton, backButton)
  }

  // Set the preferred size for the buttons
  confirmButton.prefWidth = 200
  confirmButton.prefHeight = 50
  backButton.prefWidth = 200
  backButton.prefHeight = 50

  // Create a VBox to contain all elements
  val vbox = new VBox {
    alignment = Pos.Center // Align content to the center vertically
    children = List(text, buttonBox) // Add text and buttonBox to VBox
  }

  // Create the root StackPane and add background image and the VBox
  root = new StackPane {
    children = List(backgroundImage, vbox)
  }

}