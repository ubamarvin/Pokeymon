package de.htwg.se.Pokeymon.aView.Gui.Scenes

import de.htwg.se.Pokeymon.aView.Gui.Scenes.BaseScene

import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.ControllerComponent.ControllerInterface

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

case class MainScene(controller: ControllerInterface) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val MainText = new Text("MainScene")
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val contents = controller.getSceneContent
  val player = contents.player
  val playerMon = player.getCurrentPokemon()
  val opponent = contents.opponent
  val oppMon = opponent.getCurrentPokemon()
  val roundReport = contents.roundReport
  val playerMonImage = new ImageView(s"file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/${playerMon.name}.png") {
    fitWidth = 150 // Set the width of the image
    fitHeight = 150 // Set the height of the image
  }
  val oppMonImage = new ImageView(s"file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/${oppMon.name}.png") {
    fitWidth = 150 // Set the width of the image
    fitHeight = 150 // Set the height of the image
  }

  /// ___________Main_Butttons_____________
  val attackButton = new Button("Attack") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("attack")
    }
  }

  val switchButton = new Button("Switch") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("switch")
    }
  }

  val itemButton = new Button("Item") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("item")
    }
  }

  val backButton = new Button("back") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("back")
    }
  }

  // ______creating upperscreen_________//

//____Upper Segment___________
  // oppMon Health and status
  val oppStatusText = new Text {
    text = "Health:" + oppMon.getHp() + "/100\nStatus: " + oppMon.getStatus()
    style = "-fx-font-size: 20px;"
    // You can customize the font size, color, etc. as needed
  }
  val upperLeftSegment = new VBox {
    children = Seq(oppStatusText)
    style = "-fx-background-color: rgba(211, 211, 211, 0.5);"
    alignment = Pos.CenterRight // Align text to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  // Create the buttons l
  // op mon picture
  val upperRightSegment = new VBox {
    children = Seq(
      new HBox {
        children = Seq(oppMonImage);
        alignment = Pos.CenterRight
        hgrow = Priority.Always
        fillHeight = true
      }
    )
    spacing = 10 // Spacing between rows
    alignment = Pos.CenterRight // Align buttons to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  val upperBar = new HBox {
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 150 // Set Max height to 100, this ensures it doesnt fill up the scene
    style = "-fx-background-color: rgba(0, 0, 0, 0.5);" // Set background color with opacity
    // style = "-fx-background-color: white;" // Set background color to white
    spacing = 20 // Set spacing between components
    children = Seq(upperLeftSegment, upperRightSegment)
    alignment = Pos.CenterRight
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border

  }

  // ____Middle Segment___________
  // ___player mon picture
  val playerStatusText = new Text {
    text = "Health:" + playerMon.getHp() + "/100\nStatus: " + playerMon.getStatus()
    style = "-fx-font-size: 20px;"
  }
  val middleLeftSegment = new VBox {
    children = Seq(playerStatusText)
    alignment = Pos.CenterLeft // Align text to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  // Create the buttons layout
  // __ player mon health and status
  val middleRightSegment = new VBox {
    children = Seq(
      new HBox {
        children = Seq(playerMonImage);
        alignment = Pos.CenterLeft
        hgrow = Priority.Always
        fillHeight = true
      }
    )
    spacing = 10 // Spacing between rows
    alignment = Pos.CenterLeft // Align buttons to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  val middleBar = new HBox {
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 150 // Set Max height to 100, this ensures it doesnt fill up the scene
    style = "-fx-background-color: rgba(0, 0, 0, 0.5);" // Set background color with opacity
    // style = "-fx-background-color: white;" // Set background color to white
    spacing = 20 // Set spacing between components
    children = Seq(middleRightSegment, middleLeftSegment)
    alignment = Pos.CenterLeft
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border

  }

  // _____Creating the Bottom Bar
  // Create the segments
  val textDisplay = new Text {
    text = roundReport + "What will you do?"
    style = "-fx-font-size: 20px;"
    // You can set font size and other properties as needed
  }

  // display round report here
  val leftSegment = new VBox {
    children = Seq(textDisplay)
    alignment = Pos.Center // Align text to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  // Create the buttons layout
  val rightSegment = new VBox {
    children = Seq(
      new HBox {
        children = Seq(attackButton, switchButton);
        alignment = Pos.Center
        hgrow = Priority.Always
        fillHeight = true
      },
      new HBox {
        children = Seq(itemButton, backButton);
        alignment = Pos.Center
        hgrow = Priority.Always
      }
    )
    spacing = 10 // Spacing between rows
    alignment = Pos.Center // Align buttons to the center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }

  val bar = new HBox {
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 150 // Set Max height to 100, this ensures it doesnt fill up the scene
    style = "-fx-background-color: rgba(0, 0, 0, 0.5);" // Set background color with opacity
    // style = "-fx-background-color: white;" // Set background color to white
    spacing = 20 // Set spacing between components
    children = Seq(leftSegment, rightSegment)
    alignment = Pos.Center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border

  }

  root = new StackPane { children = List(backgroundImage, bar, middleBar, upperBar) }
  upperBar.alignmentInParent_=(scalafx.geometry.Pos.TopCenter)
  middleBar.alignmentInParent_=(scalafx.geometry.Pos.CenterLeft)
  bar.alignmentInParent_=(scalafx.geometry.Pos.BottomCenter)

}
