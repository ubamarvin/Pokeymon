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

case class PickPokeScene(controller: ControllerInterface) extends BaseScene {
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")

  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val contents = controller.getSceneContent
  val pokedex = contents.pokedex
  val text = new Text("PickPoke")

  // Create the VBox for Pokémon buttons

  val buttonDone = new Button("Done") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("d")
    }
  }

  // Create a GridPane to contain Pokémon buttons and images
  val pokemonGrid = new GridPane {
    hgap = 10 // Horizontal gap between cells
    vgap = 10 // Vertical gap between cells
    alignment = Pos.Center // Align content to the center
  }

  // Create buttons with images for each Pokémon and add them to the GridPane
  for ((pokemon, index) <- pokedex.available_pokemon.zipWithIndex) {
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

  // Wrap buttonDone in an HBox with empty Region to fill space
  val bottomBox = new HBox {
    children = List(new Region(), buttonDone) // Empty Region to fill space
    HBox.setHgrow(this.children.head, Priority.Always)
    spacing = 10
    alignment = Pos.Center
  }
  StackPane.setAlignment(buttonDone, Pos.BottomCenter) // Align button to the bottom center of the StackPane

  // Create the root StackPane and add background image and the Pokémon grid
  root = new StackPane {
    children = List(backgroundImage, pokemonGrid, buttonDone)
  }

}
