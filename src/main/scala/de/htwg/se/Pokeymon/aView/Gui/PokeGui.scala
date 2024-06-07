package de.htwg.se.Pokeymon.aView.Gui

import de.htwg.se.Pokeymon.Util.Observer
import de.htwg.se.Pokeymon.Controller.Controller

import scalafx.application.JFXApp3
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{HBox, StackPane, VBox, Priority, GridPane}
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
      scene = new Scene(new StackPane(), 800, 600)
    }

  }
}

case class PickPokeScene(controller: Controller) extends BaseScene {
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")

  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val contents = controller.getSceneContent()
  val pokedex = contents.pokedex
  val text = new Text("PickPoke")

  // Create the VBox for Pokémon buttons

  val buttonDone = new Button("Done") {
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

  // Create the root StackPane and add background image and the Pokémon grid
  root = new StackPane {
    children = List(backgroundImage, pokemonGrid, buttonDone)
  }

}

case class MainScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val MainText = new Text("MainScene")
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")

  /// ___________Main_Butttons_____________
  val attackButton = new Button("Attack") {
    onAction = () => {
      controller.handleInput("attack")
    }
  }

  val switchButton = new Button("Switch") {
    onAction = () => {
      controller.handleInput("switch")
    }
  }

  val itemButton = new Button("Item") {
    onAction = () => {
      controller.handleInput("item")
    }
  }

  val backButton = new Button("back") {
    onAction = () => {
      controller.handleInput("back")
    }
  }

  // _____Creating the Bottom Bar
  // Create the segments
  val textDisplay = new Text {
    text = "FakeText: What will you do?"
    // You can set font size and other properties as needed
  }

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

  root = new StackPane { children = List(backgroundImage, bar) }
  bar.alignmentInParent_=(scalafx.geometry.Pos.BottomCenter)

}

///////////________Atck

case class AttackScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val AttackText = new Text("AttackScene")
  val screenContent = controller.getSceneContent()
  val playerMon = screenContent.player.getCurrentPokemon()
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")

  /// ___________Attack_____________
  val attackButtons: Seq[Button] = playerMon.moves.map { move =>
    new Button(move.name) {
      onAction = () => {
        controller.handleInput(move.name)
      }
    }
  }

  // Create the VBox to contain attack buttons
  val attackButtonsVBox = new VBox {
    spacing = 10
    alignment = Pos.Center
    children = attackButtons
  }

  // Create the bottom bar containing attack buttons
  val bottomBar = new HBox {
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 150 // Set Max height to 100, this ensures it doesn't fill up the scene
    style = "-fx-background-color: rgba(0, 0, 0, 0.5);" // Set background color with opacity
    spacing = 20 // Set spacing between components
    children = Seq(attackButtonsVBox)
    alignment = Pos.Center
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }
  root = new StackPane { children = List(backgroundImage, bottomBar) }
  bottomBar.alignmentInParent_=(Pos.BottomCenter)

}

////_____________switch scene
case class SwitchScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("SwitchScene")
  val screenContent = controller.getSceneContent()
  val player = screenContent.player

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

  // Create the root StackPane and add background image and the Pokémon grid
  root = new StackPane {
    children = List(backgroundImage, pokemonGrid)
  }

}

case class BattleScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("BattleScene")
  val confirmButton = new Button("Confirm") {
    onAction = () => {
      controller.handleInput("")
    }
  }

  val backButton = new Button("Back") {
    onAction = () => {
      controller.handleInput("z")
    }
  }

  root = new StackPane {
    children = List(backgroundImage, confirmButton)
  }

}

case class ItemScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("ItemScene")

  root = new StackPane {
    children = List(backgroundImage)
  }

}

case class DeadScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("Dead")

  root = new StackPane {
    children = List(backgroundImage)
  }

}

abstract class BaseScene() extends Scene(800, 600) {
  // val backgroundImage: ImageView = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png") {

}
