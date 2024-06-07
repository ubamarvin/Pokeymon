package de.htwg.se.Pokeymon.aView.Gui

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

case class OpeningScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val text = new Text("Opening")

  val confirmButton = new Button("Start Game") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    onAction = () => {
      controller.handleInput("ja")
    }
  }

  // Create an HBox to contain the buttons
  val buttonBox = new HBox {
    spacing = 20 // Spacing between buttons
    alignment = Pos.Center // Align buttons to the center horizontally
    children = List(confirmButton)
  }

  // Set the preferred size for the buttons
  confirmButton.prefWidth = 200
  confirmButton.prefHeight = 50

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

case class MainScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val MainText = new Text("MainScene")
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val contents = controller.getSceneContent()
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

///////////________Atck

case class AttackScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData
  val AttackText = new Text("AttackScene")
  val screenContent = controller.getSceneContent()
  val playerMon = screenContent.player.getCurrentPokemon()
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val contents = controller.getSceneContent()
  val player = contents.player
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

  /// ___________Attack_____________

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

  val attackButtons: Seq[Button] = playerMon.moves.map { move =>
    new Button(move.name) {
      minWidth = 150 // Increased by 50%
      prefWidth = 225 // Increased by 50%
      maxWidth = 300 // Increased by 50%
      style = "-fx-font-size: 18px;" // Increased font size by 50%
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
  root = new StackPane { children = List(backgroundImage, bottomBar, middleBar, upperBar, backButton) }
  bottomBar.alignmentInParent_=(Pos.BottomCenter)
  upperBar.alignmentInParent_=(scalafx.geometry.Pos.TopCenter)
  middleBar.alignmentInParent_=(scalafx.geometry.Pos.CenterLeft)

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

  // Create the root StackPane and add background image and the Pokémon grid
  root = new StackPane {
    children = List(backgroundImage, pokemonGrid, backButton)
  }

}

case class BattleScene(controller: Controller) extends BaseScene {
  // backgroundImage.fitWidth = 800 // Match the scene width
  // backgroundImage.fitHeight = 600 // Match the scene height
  // gameData = controller.game.getData

  val screenContent = controller.getSceneContent()
  val playerMon = screenContent.player.getCurrentPokemon()
  val backgroundImage = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png")
  val contents = controller.getSceneContent()
  val player = contents.player
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

  /// ___________Buttons_____________

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
  // val playerStatusText = new Text {
  //  text = "Health:" + playerMon.getHp() + "/100\nStatus: " + playerMon.getStatus()
  //  style = "-fx-font-size: 20px;"
  // }
  val middleLeftSegment = new VBox {
    children = Seq(new Text {
      text = "Health:" + playerMon.getHp() + "/100\nStatus: " + playerMon.getStatus()
      style = "-fx-font-size: 20px;"
    })
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
  val confirmButton = new Button("Confirm") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    // alignment = Pos.CenterLeft
    onAction = () => {
      controller.handleInput("")
    }
  }

  val backButton = new Button("Back") {
    minWidth = 150 // Increased by 50%
    prefWidth = 225 // Increased by 50%
    maxWidth = 300 // Increased by 50%
    style = "-fx-font-size: 18px;" // Increased font size by 50%
    // alignment = Pos.CenterRight
    onAction = () => {
      controller.undo
    }
  }

  // Create the bottom bar containing attack buttons
  val bottomBar = new HBox {
    prefHeight = 100 // Set preferred height for the bottom bar
    maxHeight = 150 // Set Max height to 100, this ensures it doesn't fill up the scene
    style = "-fx-background-color: rgba(0, 0, 0, 0.5);" // Set background color with opacity
    spacing = 10 // Set spacing between components
    children = Seq(confirmButton, backButton)
    // alignment = Pos.BottomCenter
    style = "-fx-border-color: blue;" // Add blue border around the box
    padding = scalafx.geometry.Insets(10) // Add padding for the border
  }
  root = new StackPane { children = List(backgroundImage, bottomBar, middleBar, upperBar, backButton) }
  bottomBar.alignmentInParent_=(Pos.BottomCenter)
  upperBar.alignmentInParent_=(scalafx.geometry.Pos.TopCenter)
  middleBar.alignmentInParent_=(scalafx.geometry.Pos.CenterLeft)
  StackPane.setAlignment(confirmButton, Pos.BottomLeft) // Align button to the bottom center of the StackPane
  StackPane.setAlignment(backButton, Pos.BottomRight) // Alig
}

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

abstract class BaseScene() extends Scene(800, 600) {
  // val backgroundImage: ImageView = new ImageView("file:src/main/scala/de/htwg/se/Pokeymon/aView/Gui/Pics/background.png") {

}
