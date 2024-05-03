import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
/*
class TrainerSpec extends AnyWordSpec {
  val empty_move = Move("empty", 0)
  val tackle = Move("tackle", 50)
  val thunder = Move("thunder", 70)
  val bodyslam = Move("bodyslam", 30)
  val kick = Move("kick", 10)

  val pikachu_moves: List[Move] = List(tackle, thunder)
  val pikachu = Pokemon(1, "pikachu", 100, pikachu_moves, 30)

  val rat_moves: List[Move] = List(bodyslam, kick)
  val ratmon = Pokemon(2, "ratmon", 100, rat_moves, 40)

  val evoli_moves: List[Move] = List(bodyslam, kick)
  val evoli = Pokemon(4, "evoli", 100, evoli_moves, 35)

  val trainer = Trainer(Vector(pikachu, ratmon))

  "A Trainer" when {
    "created" should {
      "have correct initial values" in {
        trainer.pokemons.size shouldEqual 2
        trainer.currentPokemon shouldEqual pikachu
      }
    }
    "removes a Pokemon" should {
      "correctly remove the specified Pokemon" in {
        val updatedTrainer = trainer.removePokemon("pikachu")
        updatedTrainer.pokemons.size shouldEqual 1
        updatedTrainer.pokemons.headOption shouldEqual Some(ratmon)
      }
    }
    "Trainer's toString" should {
      "return a string representation of the trainer's pokemons" in {
        val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 80)), 90)
        val charmander = Pokemon(2, "Charmander", 100, List(Move("Ember", 40)), 70)
        val bulbasaur = Pokemon(3, "Bulbasaur", 100, List(Move("Vine Whip", 45)), 65)
        val trainer = Trainer(Vector(pikachu, charmander, bulbasaur))

        val expectedString = "Trainer's Pokemons: Pikachu (100), Charmander (100), Bulbasaur (100)"

        val result = trainer.toString

        result shouldBe expectedString
      }

      "return 'Trainer's Pokemons: ' when the trainer has no pokemons" in {
        val trainer = Trainer(Vector())

        val expectedString = "Trainer's Pokemons: "

        val result = trainer.toString

        result shouldBe expectedString
      }
    }
    "getNextPokemon" should {
      "return the next available Pokemon in the Trainer's list" in {
        val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 80)), 90)
        val bulbasaur = Pokemon(2, "Bulbasaur", 100, List(Move("Vine Whip", 45)), 65)
        val charmander = Pokemon(3, "Charmander", 100, List(Move("Ember", 40)), 70)
        val trainer = Trainer(Vector(pikachu, bulbasaur, charmander))

        val nextPokemon = trainer.getNextPokemon()

        nextPokemon shouldBe pikachu
      }

      "return an empty Pokemon if the Trainer's list is empty" in {
        val emptyPokemon = Pokemon(-1, "Empty", 0, List(), 0) // Create an empty Pokemon object
        val trainer = Trainer(Vector())

        val nextPokemon = trainer.getNextPokemon()

        nextPokemon shouldBe emptyPokemon
      }
    }
    "adds a Pokemon" should {
      "correctly add the new Pokemon" in {
        val newPokemon = Pokemon(3, "charizard", 100, List(tackle), 35)
        val updatedTrainer = trainer.addPokemon(newPokemon)
        updatedTrainer.pokemons.size shouldEqual 3
        updatedTrainer.pokemons.lastOption shouldEqual Some(newPokemon)
      }
    }
    "sets the current Pokemon" should {
      "correctly update the current Pokemon" in {
        val newCurrentPokemon = ratmon
        val updatedTrainer = trainer.setCurrentPokemon(newCurrentPokemon)
        updatedTrainer.currentPokemon shouldEqual newCurrentPokemon
      }
    }
    "isAlive" should {
      "return true if the Pokemon has positive HP" in {
        val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 80)), 90)

        val result = pikachu.isAlive()

        result shouldBe true
      }

      "return false if the Pokemon has 0 HP" in {
        val charmander = Pokemon(2, "Charmander", 0, List(Move("Ember", 40)), 70)

        val result = charmander.isAlive()

        result shouldBe false
      }

      "return false if the Pokemon has negative HP" in {
        val bulbasaur = Pokemon(3, "Bulbasaur", -20, List(Move("Vine Whip", 45)), 65)

        val result = bulbasaur.isAlive()

        result shouldBe false
      }
    }

    "attack" should {
      "return Some(power) if the move exists" in {
        val charmander = Pokemon(2, "Charmander", 100, List(Move("Ember", 40)), 70)

        val result = charmander.attack("Ember")

        result shouldBe Some(40)
      }

      "return None if the move does not exist" in {
        val bulbasaur = Pokemon(3, "Bulbasaur", 100, List(Move("Vine Whip", 45)), 65)

        val result = bulbasaur.attack("Flamethrower")

        result shouldBe None
      }
    }

    "setCurrentMove" should {
      "return a new Pokemon instance with the specified move if it exists" in {
        val bulbasaur = Pokemon(3, "Bulbasaur", 100, List(Move("Vine Whip", 45), Move("Tackle", 35)), 65)

        val updatedBulbasaur = bulbasaur.setCurrentMove("Vine Whip")

        updatedBulbasaur.currentMove.name shouldBe "Vine Whip"
      }

      "return a new Pokemon instance with an empty move if the specified move does not exist" in {
        val bulbasaur = Pokemon(3, "Bulbasaur", 100, List(Move("Vine Whip", 45), Move("Tackle", 35)), 65)

        val updatedBulbasaur = bulbasaur.setCurrentMove("Razor Leaf")

        updatedBulbasaur.currentMove.name shouldBe "empty"
      }
    }
    "checks if there are Pokemon left" should {
      "return true if there are Pokemon left" in {
        trainer.hasPokemonleft() shouldEqual true
      }
      "return false if there are no Pokemon left" in {
        val emptyTrainer = Trainer(Vector())
        emptyTrainer.hasPokemonleft() shouldEqual false
      }
    }
    "pickYourPokemons" should {
      "allow the player to pick one Pokemon" in {
        val player = Trainer(Vector())
        val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

        val mockInput = new java.io.ByteArrayInputStream("pikachu\nq\n".getBytes)
        Console.withIn(mockInput) {
          val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

          updatedPlayer.pokemons shouldBe Vector(pikachu)
          updatedPokedex.showAvailablePokemon() shouldBe "ratmon, cowpokemon, evoli"
          numOfPokemons shouldBe 1
        }
      }

      "allow the player to pick multiple Pokemon until they choose to finish" in {
        val player = Trainer(Vector())
        val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

        val mockInput = new java.io.ByteArrayInputStream("pikachu\nratmon\ncowpokemon\nq\n".getBytes)
        Console.withIn(mockInput) {
          val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

          updatedPlayer.pokemons shouldBe Vector(pikachu, ratmon, cowPokemon)
          updatedPokedex.showAvailablePokemon() shouldBe "evoli"
          numOfPokemons shouldBe 3
        }
      }

      "not allow the player to pick more than six Pokemon" in {
        val player = Trainer(Vector())
        val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

        val mockInput = new java.io.ByteArrayInputStream("pikachu\nratmon\ncowpokemon\npikachu\nevoli\ncharmander\n".getBytes)
        Console.withIn(mockInput) {
          val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

          updatedPlayer.pokemons shouldBe Vector(pikachu, ratmon, cowPokemon, evoli)
          updatedPokedex.showAvailablePokemon() shouldBe ""
          numOfPokemons shouldBe 4
        }
      }

      "not allow the player to finish without picking at least one Pokemon" in {
        val player = Trainer(Vector())
        val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

        val mockInput = new java.io.ByteArrayInputStream("q\n".getBytes)
        Console.withIn(mockInput) {
          val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

          updatedPlayer.pokemons shouldBe Vector()
          updatedPokedex.showAvailablePokemon() shouldBe "pikachu, ratmon, cowpokemon, evoli"
          numOfPokemons shouldBe 0
        }
      }

      "not allow the player to pick a non-existent Pokemon" in {
        val player = Trainer(Vector())
        val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

        val mockInput = new java.io.ByteArrayInputStream("charizard\npikachu\nq\n".getBytes)
        Console.withIn(mockInput) {
          val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

          updatedPlayer.pokemons shouldBe Vector(pikachu)
          updatedPokedex.showAvailablePokemon() shouldBe "ratmon, cowpokemon, evoli"
          numOfPokemons shouldBe 1
        }
      }
      "Pokedex" should {
        "show available Pokemon correctly" in {
          val pokedex = new Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

          val availablePokemon = pokedex.showAvailablePokemon()

          availablePokemon shouldBe "pikachu, ratmon, cowpokemon, evoli"
        }

        "choose a Pokemon and update the Pokedex accordingly" in {
          val pokedex = new Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

          val (chosenPokemon, updatedPokedex) = pokedex.choosePokemon("pikachu")

          chosenPokemon shouldBe pikachu
          updatedPokedex.showAvailablePokemon() shouldBe "ratmon, cowpokemon, evoli"
        }

        "return false when checking for a non-existent Pokemon" in {
          val pokedex = new Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

          val exists = pokedex.exists("charizard")

          exists shouldBe false
        }

        "return true when checking for an existent Pokemon" in {
          val pokedex = new Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

          val exists = pokedex.exists("pikachu")

          exists shouldBe true
        }
      }
      "evaluateRound" should {
        "correctly match the player and opponent Pokemon by ID" in {
          val playerPokemon = pikachu
          val opponentPokemon = cowPokemon

          val (matchedPlayer, matchedOpponent) = evaluateRound(playerPokemon, opponentPokemon)

          matchedPlayer shouldBe playerPokemon
          matchedOpponent shouldBe opponentPokemon
        }

        "calculate damage and update the Pokemon accordingly" in {
          val playerPokemon = pikachu.copy(hp = 100)
          val opponentPokemon = cowPokemon.copy(hp = 100)

          val (updatedPlayer, updatedOpponent) = evaluateRound(playerPokemon, opponentPokemon)

          updatedPlayer.hp shouldBe (playerPokemon.hp - opponentPokemon.currentMove.power)
          updatedOpponent.hp shouldBe (opponentPokemon.hp - playerPokemon.currentMove.power)
        }
      }
      "evaluateRound" should {
        // Andere Tests hier ...

        "calculate damage and update HP for both attacking Pokemon" in {
          val firstMover = pikachu.copy(currentMove = tackle, hp = 80)
          val secondMover = cowPokemon.copy(currentMove = thunder, hp = 70)

          val (updatedFirstMover, updatedSecondMover) = evaluateRound(firstMover, secondMover)

          val expectedFirstMoverHP = firstMover.hp - secondMover.currentMove.power
          val expectedSecondMoverHP = secondMover.hp - firstMover.currentMove.power

          updatedFirstMover.hp shouldBe expectedFirstMoverHP
          updatedSecondMover.hp shouldBe expectedSecondMoverHP
        }
      }
      "determineSlowerPokemon" should {
        "return the slower of two Pokemon based on speed" in {
          val slowerPokemon = ratmon
          val fasterPokemon = pikachu

          val result = determineSlowerPokemon(fasterPokemon, slowerPokemon)

          result shouldBe slowerPokemon
        }

        "return the first Pokemon if their speeds are equal" in {
          val firstPokemon = ratmon
          val secondPokemon = cowPokemon

          val result = determineSlowerPokemon(firstPokemon, secondPokemon)

          result shouldBe firstPokemon
        }
      }

      "determineFasterPokemon" should {
        "return the faster of two Pokemon based on speed" in {
          val slowerPokemon = ratmon
          val fasterPokemon = pikachu

          val result = determineFasterPokemon(fasterPokemon, slowerPokemon)

          result shouldBe fasterPokemon
        }

        "return the first Pokemon if their speeds are equal" in {
          val firstPokemon = ratmon
          val secondPokemon = cowPokemon

          val result = determineFasterPokemon(firstPokemon, secondPokemon)

          result shouldBe firstPokemon
        }
      }
      "generatePokemonField" should {
        "return a formatted string representing the Pokemon field" in {
          val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 80)), 90)
          val charmander = Pokemon(2, "Charmander", 100, List(Move("Ember", 40)), 70)
          val player = Trainer(Vector(pikachu))
          val opponent = Trainer(Vector(charmander))

          val fieldString = generatePokemonField(pikachu, charmander, player, opponent)

          val expectedFieldString =
            "+------------------------------+\n" +
              "                Charmander (100)\n " +
              " Trainer's Pokemons: Charmander (100)\n" +
              "\n" +
              "Pikachu (100)\n Trainer's Pokemons: Pikachu (100)\n" +
              "+------------------------------+\n"

          fieldString shouldBe expectedFieldString
        }
      }
      "switchIfdead" should {
        "remove the dead Pokemon and set the next available Pokemon as current if the trainer has more than one Pokemon" in {
          val pikachu = Pokemon(1, "Pikachu", 0, List(Move("Thunderbolt", 80)), 90)
          val charmander = Pokemon(2, "Charmander", 100, List(Move("Ember", 40)), 70)
          val trainer = Trainer(Vector(pikachu, charmander))

          val updatedTrainer = switchIfdead(trainer)

          updatedTrainer.pokemons shouldBe Vector(charmander)
          updatedTrainer.currentPokemon shouldBe charmander
        }

        "remove the dead Pokemon if the trainer has only one Pokemon left" in {
          val pikachu = Pokemon(1, "Pikachu", 0, List(Move("Thunderbolt", 80)), 90)
          val trainer = Trainer(Vector(pikachu))

          val updatedTrainer = switchIfdead(trainer)

          updatedTrainer.pokemons shouldBe Vector.empty
        }

        "not modify the trainer if the current Pokemon is alive" in {
          val pikachu = Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 80)), 90)
          val trainer = Trainer(Vector(pikachu))

          val updatedTrainer = switchIfdead(trainer)

          updatedTrainer shouldBe trainer
        }
      }
      "prepareForBattle" should {
        "initialize the battle by letting the player choose their Pokemon and setting up the opponent" in {
          val player = Trainer(Vector(Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 90)), 70)))
          val pokedex = Pokedex(Vector(Pokemon(2, "Bulbasaur", 80, List(Move("Vine Whip", 45)), 50)))
          val mockInput = new java.io.ByteArrayInputStream("Pikachu\n".getBytes)
          Console.withIn(mockInput) {
            val result = prepareForBattle(player)
            // Assert that the battle mode is initiated
            result shouldBe "Initiating battle..."
          }
        }

        "pickYourPokemons" should {
          "not allow the player to pick more than six Pokemon" in {
            val player = Trainer(Vector())
            val pokedex = Pokedex(Vector(pikachu, ratmon, cowPokemon, evoli))

            // Simulate user input trying to pick more than six Pokemon
            val mockInput = new java.io.ByteArrayInputStream("pikachu\nratmon\ncowpokemon\npikachu\nevoli\ncharmander\n".getBytes)
            Console.withIn(mockInput) {
              val (updatedPlayer, updatedPokedex, numOfPokemons) = pickYourPokemons(player, pokedex)

              // Assert that the player's Pokemon list remains unchanged
              updatedPlayer.pokemons shouldBe Vector(pikachu, ratmon, cowPokemon, evoli)
              // Assert that there are still available Pokemon left in the pokedex
              numOfPokemons shouldBe 4
            }
          }
        }
        "battleMode" should {
          "simulate a battle between two trainers" in {
            val player = Trainer(Vector(Pokemon(1, "Pikachu", 100, List(Move("Thunderbolt", 90)), 80)))
            val opponent = Trainer(Vector(Pokemon(2, "Bulbasaur", 80, List(Move("Vine Whip", 45)), 60)))

            // Simulate user input for player's move
            val mockInput = new java.io.ByteArrayInputStream("Thunderbolt\n".getBytes)
            Console.withIn(mockInput) {
              val result = battleMode(player, opponent)

              // Assert that the battle mode output contains some expected strings
              result should include("Player has Pokémon left:")
              result should include("Opponent has Pokémon left:")
            }
          }
          "switchIfdead" should {
            "remove the current Pokemon and set the next Pokemon as current if the current Pokemon is dead and there are more than one Pokemon in the Trainer's list" in {
              val deadPokemon = Pokemon(1, "Deadmon", 0, List(Move("Fainted", 0)), 50)
              val alivePokemon = Pokemon(2, "Alive", 100, List(Move("Attack", 50)), 70)
              val trainer = Trainer(Vector(deadPokemon, alivePokemon))

              val updatedTrainer = switchIfdead(trainer)

              updatedTrainer.pokemons shouldBe Vector(alivePokemon)
              updatedTrainer.currentPokemon shouldBe alivePokemon
            }

            "remove the current Pokemon if it is dead and there is only one Pokemon in the Trainer's list" in {
              val deadPokemon = Pokemon(1, "Deadmon", 0, List(Move("Fainted", 0)), 50)
              val trainer = Trainer(Vector(deadPokemon))

              val updatedTrainer = switchIfdead(trainer)

              updatedTrainer.pokemons shouldBe empty
            }

            "do nothing if the current Pokemon is alive" in {
              val alivePokemon = Pokemon(1, "Alive", 100, List(Move("Attack", 50)), 70)
              val trainer = Trainer(Vector(alivePokemon))

              val updatedTrainer = switchIfdead(trainer)

              updatedTrainer shouldBe trainer
            }
          }
          "setCurrentMove" should {
            "set the current move of the Pokemon correctly" in {
              val pikachu = Pokemon(25, "Pikachu", 100, List(Move("Thunderbolt", 90)), 90)

              val updatedPikachu = pikachu.setCurrentMove("tackle")

              updatedPikachu.currentMove.name shouldBe "tackle"
            }
          }
        }
      }
    }
  }
}
*/