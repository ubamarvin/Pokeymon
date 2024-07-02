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

case class SwitchScene(controller: ControllerInterface) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("SwitchScene")
  val screenContent = controller.getSceneContent
  val player = screenContent.player

  val backButton = new Button("back") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("back")
    }
  }
  StackPane.setAlignment(backButton, Pos.BottomCenter) // Align button to the bottom center of the StackPa
  // Create a GridPane to contain Pokémon buttons and images
  val pokemonGrid = new GridPane {
    hgap = 10 // Horizontal gap between cells
    vgap = 10 // Vertical gap between cells
    alignment = Pos.Center // Align content to the center
  }

  // Create buttons with images for each Pokémon and add them to the GridPane
  for ((pokemon, index) <- player.getPokemons().zipWithIndex) {
    val button = new Button(pokemon.name) {
      minWidth = 100 // Set a minimum width for the button
      prefWidth = 150 // Set a preferred width for the button
      maxWidth = 200 // Set a maximum width for the button
      onAction = () => {
        controller.handleInput(pokemon.name)
      }
    }

    // Load the Pokémon's image dynamically (replace "placeholder.png" with actual image path)
    val pokemonImage = new ImageView(s"file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/${pokemon.name}.png") {
      fitWidth = 100 // Set the width of the image
      fitHeight = 100 // Set the height of the image
    }

    // Wrap the Pokémon image and button in an HBox
    val vbox = new VBox {
      children = List(pokemonImage, button)
      spacing = 5 // Spacing between image and button
      alignment = Pos.Center // Align content to the center
    }

    // Add the HBox containing image and button to the GridPane at specific row and column
    pokemonGrid.add(vbox, index % 3, index / 3) // Adjust column count as needed
  }
  val saveButton = new Button("save") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.save
    }
  }

  val loadButton = new Button("load") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.load
    }
  }
  val dataBar = new HBox {
    children = Seq(saveButton, loadButton)
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 50
    padding = scalafx.geometry.Insets(10)
  }

  // Create the root StackPane and add background image and the Pokémon grid
  root = new StackPane {
    children = List(backgroundImage, pokemonGrid, backButton, dataBar)
  }
  dataBar.alignmentInParent_=(scalafx.geometry.Pos.TopLeft)

}
