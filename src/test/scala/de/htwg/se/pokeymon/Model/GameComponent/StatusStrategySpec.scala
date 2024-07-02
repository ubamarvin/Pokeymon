package de.htwg.se.Pokeymon.Model.GameComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Pokeymon.Model.GameData._

class StatusStrategySpec extends AnyWordSpec with Matchers {
  "A NormalState" should {
    "have no status name" in {
      val normal = NormalState()
      normal.statusName should be ("")
    }
    
    "not affect the Pokémon" in {
      val normal = NormalState()
      val pokemon = Pokemon("Pikachu", 100, List.empty, new StatusEffectStrategyContext(normal))
      val (affectedPokemon, msg) = normal.applyEffect(pokemon)
      affectedPokemon should be (pokemon)
      msg should be ("")
    }
  }

  "A BurnedState" should {
    "reduce the Pokémon's HP by the intensity" in {
      val burned = BurnedState(3, 10)
      val pokemon = Pokemon("Charmander", 50, List.empty, new StatusEffectStrategyContext(burned))
      val (affectedPokemon, msg) = burned.applyEffect(pokemon)
      affectedPokemon.hp should be (40)
      msg should be ("-Charmanderis hurt by burn, lost 10 HP\n")
    }
    
    "reduce its duration after application" in {
      val burned = BurnedState(3, 10)
      val pokemon = Pokemon("Charmander", 50, List.empty, new StatusEffectStrategyContext(burned))
      val (affectedPokemon, _) = burned.applyEffect(pokemon)
      affectedPokemon.status.strategy.duration should be (2)
    }
    
    "clear the effect when duration is 0" in {
      val burned = BurnedState(1, 10)
      val pokemon = Pokemon("Charmander", 50, List.empty, new StatusEffectStrategyContext(burned))
      val (affectedPokemon, _) = burned.applyEffect(pokemon)
      affectedPokemon.status.strategy shouldBe a [NormalState]
    }
  }

  "A PoisonedState" should {
    "reduce the Pokémon's HP by the intensity" in {
      val poisoned = PoisonedState(3, 10)
      val pokemon = Pokemon("Bulbasaur", 50, List.empty, new StatusEffectStrategyContext(poisoned))
      val (affectedPokemon, msg) = poisoned.applyEffect(pokemon)
      affectedPokemon.hp should be (40)
      msg should be ("-Bulbasauris hurt by poison, lost 10 HP\n")
    }
    
    "reduce its duration after application" in {
      val poisoned = PoisonedState(3, 10)
      val pokemon = Pokemon("Bulbasaur", 50, List.empty, new StatusEffectStrategyContext(poisoned))
      val (affectedPokemon, _) = poisoned.applyEffect(pokemon)
      affectedPokemon.status.strategy.duration should be (2)
    }
    
    "clear the effect when duration is 0" in {
      val poisoned = PoisonedState(1, 10)
      val pokemon = Pokemon("Bulbasaur", 50, List.empty, new StatusEffectStrategyContext(poisoned))
      val (affectedPokemon, _) = poisoned.applyEffect(pokemon)
      affectedPokemon.status.strategy shouldBe a [NormalState]
    }
  }

  "A SleepState" should {
    "not reduce the Pokémon's HP" in {
      val sleep = SleepState(3)
      val pokemon = Pokemon("Snorlax", 100, List.empty, new StatusEffectStrategyContext(sleep))
      val (affectedPokemon, msg) = sleep.applyEffect(pokemon)
      affectedPokemon.hp should be (100)
      msg should be ("-Snorlax is sleeping\n")
    }
    
    "reduce its duration after application" in {
      val sleep = SleepState(3)
      val pokemon = Pokemon("Snorlax", 100, List.empty, new StatusEffectStrategyContext(sleep))
      val (affectedPokemon, _) = sleep.applyEffect(pokemon)
      affectedPokemon.status.strategy.duration should be (2)
    }
    
    "clear the effect when duration is 0" in {
      val sleep = SleepState(1)
      val pokemon = Pokemon("Snorlax", 100, List.empty, new StatusEffectStrategyContext(sleep))
      val (affectedPokemon, _) = sleep.applyEffect(pokemon)
      affectedPokemon.status.strategy shouldBe a [NormalState]
    }
  }

  "A ParalyzedState" should {
    "not reduce the Pokémon's HP" in {
      val paralyzed = ParalyzedState(3)
      val pokemon = Pokemon("Raichu", 100, List.empty, new StatusEffectStrategyContext(paralyzed))
      val (affectedPokemon, msg) = paralyzed.applyEffect(pokemon)
      affectedPokemon.hp should be (100)
      msg should be ("-Raichu is paralyzed\n")
    }
    
    "reduce its duration after application" in {
      val paralyzed = ParalyzedState(3)
      val pokemon = Pokemon("Raichu", 100, List.empty, new StatusEffectStrategyContext(paralyzed))
      val (affectedPokemon, _) = paralyzed.applyEffect(pokemon)
      affectedPokemon.status.strategy.duration should be (2)
    }
    
    "clear the effect when duration is 0" in {
      val paralyzed = ParalyzedState(1)
      val pokemon = Pokemon("Raichu", 100, List.empty, new StatusEffectStrategyContext(paralyzed))
      val (affectedPokemon, _) = paralyzed.applyEffect(pokemon)
      affectedPokemon.status.strategy shouldBe a [NormalState]
    }
  }
}
