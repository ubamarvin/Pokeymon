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

case class ItemScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("ItemScene")
  val backButton = new Button("back") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("back")
    }
  }
  StackPane.setAlignment(backButton, Pos.BottomCenter) // Align button to the bottom center of the StackPane

  root = new StackPane {
    children = List(backgroundImage, backButton)
  }

}
