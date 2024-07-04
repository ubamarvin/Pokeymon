# Pokémon Battle Mode

[![Coverage Status](https://coveralls.io/repos/github/ubamarvin/Pokeymon/badge.svg)](https://coveralls.io/github/ubamarvin/Pokeymon)

## Overview

Welcome to Pokémon Battle Mode, a Scala-based game developed for the Software Engineering classes at HTWG Konstanz. The game simulates Pokémon battles, allowing players to engage in strategic combat using various Pokémon and their unique moves.

## Developers

- Marvin Uba
- Kadir Hasanovic

## Features

- **Multiple Scenes:** The game includes various scenes such as Main Menu, Battle Scene, Attack Scene, and more.
- **Command-Based Input Handling:** Supports various commands for game actions like attacking, using items, switching Pokémon, saving, and loading the game.
- **Undo/Redo Functionality:** Allows players to undo and redo their actions.
- **Observer Pattern:** Implements the observer pattern for updating game views.

## Installation

### Prerequisites

- Scala (version 2.13.x or later)
- SBT (Scala Build Tool)
- Java JDK (version 8 or later)

### Clone the Repository

```sh
git clone https://github.com/your-repo/pokemon-battle-mode.git
cd pokemon-battle-mode
```

### Build the Project

```sh
sbt compile
```

### Run the Game

```sh
sbt run
```
## Test

## Test commands 

```sh
sbt clean coverage test
```

```sh
sbt coverageReport
```

## Continuous Integration

We use GitHub Actions for continuous integration. The workflow is defined in the .GitHub/workflows/scala.yml file.

## Project Structure

```plaintext
├── src
│   ├── main
│   │   ├── scala
│   │   │   ├── de
│   │   │   │   ├── htwg
│   │   │   │   │   ├── se
│   │   │   │   │   │   ├── Pokeymon
│   │   │   │   │   │   │   ├── Controller
│   │   │   │   │   │   │   │   ├── ControllerComponent
│   │   │   │   │   │   │   │   │   ├── ControllerBaseImplementation
│   │   │   │   │   │   │   │   │   └── ControllerInterface.scala
│   │   │   │   │   │   │   │   ├── GameComponent
│   │   │   │   │   │   │   │   │   ├── Game.scala
│   │   │   │   │   │   │   │   │   ├── GameData
│   │   │   │   │   │   │   │   ├── Util
│   │   │   │   │   │   │   │   ├── aView
│   │   │   │   │   │   │   │   │   ├── Gui
│   │   │   │   │   │   │   │   │   │   ├── Gui.scala
│   │   │   │   │   │   │   │   │   │   ├── Scenes
│   │   │   │   │   │   │   │   │   ├── Tui
│   │   │   │   │   │   │   │   │   │   └── Tui.scala
│   ├── test
│   │   ├── scala
│   │   │   ├── de
│   │   │   │   ├── htwg
│   │   │   │   │   ├── se
│   │   │   │   │   │   ├── Pokeymon
│   │   │   │   │   │   │   ├── Controller
│   │   │   │   │   │   │   │   ├── ControllerComponent
│   │   │   │   │   │   │   │   │   └── ControllerBaseImplementationSpec.scala
│   │   │   │   │   │   │   │   ├── GameComponent
│   │   │   │   │   │   │   │   │   └── GameSpec.scala
│   │   │   │   │   │   │   │   ├── Util
│   │   │   │   │   │   │   │   ├── aView
│   │   │   │   │   │   │   │   │   ├── Gui
│   │   │   │   │   │   │   │   │   │   ├── GuiSpec.scala
│   │   │   │   │   │   │   │   │   │   ├── Scenes
│   │   │   │   │   │   │   │   │   │   │   ├── AttackSceneSpec.scala
│   │   │   │   │   │   │   │   │   ├── Tui
│   │   │   │   │   │   │   │   │   │   └── TuiSpec.scala
```

## How to Play

1. **Main Menu:** Start the game from the main menu. You can choose to start a new game or load an existing one.
2. **Pick Pokémon:** Choose your Pokémon for the battle.
3. **Battle Scene:** Engage in battles using your Pokémon's moves. Use commands to attack, switch Pokémon, use items, or manage your game.
4. **Commands:**
    - `q`: Quit the game.
    - `z`: Undo the last action.
    - `y`: Redo the undone action.
    - `save`: Save the current game state.
    - `load`: Load a previously saved game state.
5. **Winning the Game:** Defeat all opponent Pokémon to win the game.

## Screenshots

<img width="612" alt="Bildschirm­foto 2024-07-04 um 05 14 34" src="https://github.com/ubamarvin/Pokeymon/assets/164490881/60c6f4f3-7b2a-4a99-af44-367e24f38123">
<img width="515" alt="Bildschirm­foto 2024-07-04 um 05 15 40" src="https://github.com/ubamarvin/Pokeymon/assets/164490881/3c3ea6df-b36c-4d04-a7d3-42c2f907422b">
<img width="541" alt="Bildschirm­foto 2024-07-04 um 05 16 15" src="https://github.com/ubamarvin/Pokeymon/assets/164490881/80a93752-d737-4729-a00a-1d10a38cb2f5">
<img width="583" alt="Bildschirm­foto 2024-07-04 um 05 16 40" src="https://github.com/ubamarvin/Pokeymon/assets/164490881/86448f45-4f4d-4365-950a-8cfd548396a8">

## Rules 

General Rules:
- Two trainers battle against each other.
- Each trainer has a team of up to six Pokémon.
- Goal: Make all of the opponent's Pokémon faint (K.O.).
Choosing Pokemon:
- At the start of the battle, each trainer selects a Pokémon from their team to start the fight.
- During the battle, trainers can switch their active Pokémon.
Turn-Based Combat:
- Each trainer chooses one action per turn: attack, use an item, or switch Pokémon.
- Actions are executed in the order of the Pokémon's speed.
Victory Conditions:
- A Pokémon faints when its HP drops to 0.
- A trainer loses if all their Pokémon are fainted.
- A trainer wins if all of the opponent's Pokémon are fainted.


## Contributions

If you want to contribute to this project, you can fork this repository and create a pull request. We are happy about every contribution. For other changes, please open an issue.
We welcome contributions to enhance the game. Feel free to fork the repository and submit pull requests with improvements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.


We hope you enjoy playing Pokémon Battle Mode!
