package de.htwg.se.Pokeymon.Model.GameComponent
import de.htwg.se.Pokeymon.Model
import de.htwg.se.Pokeymon.Model.GameData._
import de.htwg.se.Pokeymon.Model.GameComponent._
// Status Strategy
// Pokemon can be with no status, burned, poisoned, asleep or paralyzed
// Based on the status the POkemon will behave differently befor or after it executes its move
// here i will focus solely on what will happen after the move
// focusing on how the status applys before the move requires a little bit more overhead (and time)

//On before
//Pokemon get additional instanz val: val boolean canMove
//if Pokemon is asleep or paralyzed canMove is set to false
//Attack Mediator / Attack Strategy checks if Pokemon can move and if not extracts from the status what its status is
// >>> "Pikachu is asleep"

trait StatusStrategy {
  def statusName: String
  def duration: Int
  def applyEffect(pokemon: Pokemon): (Pokemon, String)
  def clearEffect(pokemon: Pokemon): Pokemon
  def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy
}

case class StatusEffectStrategyContext(strategy: StatusStrategy = new NormalState) {
  def setStatusContext(newStrategy: StatusStrategy): StatusEffectStrategyContext =
    this.copy(strategy = newStrategy)

  def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    strategy.applyEffect(pokemon)

  def clearEffect(pokemon: Pokemon): Pokemon =
    strategy.clearEffect(pokemon)

  def getStatusName(): String = strategy.statusName
}

// return the Pokemon unharmed
case class NormalState() extends StatusStrategy {
  override def duration: Int = 0
  override def statusName: String = ""

  override def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    (pokemon, "")
  override def clearEffect(pokemon: Pokemon): Pokemon =
    pokemon
  override def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy =
    statusStrategy
}

// Poisoned and Burned are applied at the end of the turn.

//__________Burned
// @duration: Since a Pokemnn cant be burned forever.
// @Intensity: Intensity at which it burns. Not all burns are equally intense, some may change over time?
case class BurnedState(duration: Int, intensity: Int = 5) extends StatusStrategy {
  override def statusName: String = "burned"

  override def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    // apply effect
    val damagedPokemon = pokemon.decreaseHp(intensity)
    val msg = "-" + pokemon.name + "is hurt by burn, lost " + intensity + " HP\n"
    // reduceDuration
    val updStatusStrategy = reduceDuration(this)
    // Re-initialize based on duration
    val updPokemon = if (updStatusStrategy.duration > 0) { damagedPokemon.setStatus(updStatusStrategy) }
    else { damagedPokemon.setStatus(new NormalState) }
    (updPokemon, msg)

  override def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy =
    copy(duration = duration - 1)

  override def clearEffect(pokemon: Pokemon): Pokemon =
    pokemon.copy(status = new StatusEffectStrategyContext)
}

//_______Poisoned
case class PoisonedState(duration: Int, intensity: Int = 5) extends StatusStrategy {
  override def statusName: String = "poisoned"

  override def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    // apply effect
    val damagedPokemon = pokemon.decreaseHp(intensity)
    val msg = "-" + pokemon.name + "is hurt by poison, lost " + intensity + " HP\n"

    // reduceDuration
    val updStatusStrategy = reduceDuration(this)
    // Re-initialize based on duration
    val updPokemon = if (updStatusStrategy.duration > 0) { damagedPokemon.setStatus(updStatusStrategy) }
    else { damagedPokemon.setStatus(new NormalState) }
    (updPokemon, msg)

  override def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy =
    copy(duration = duration - 1)

  override def clearEffect(pokemon: Pokemon): Pokemon =
    pokemon.copy(status = new StatusEffectStrategyContext)
}

// Sleep and Paralyzed are applied at the beginning of AttackChoiceHandler -> attackStrategy.inflict damage
// If status state is sleep state then cant move oder sooo
case class SleepState(duration: Int) extends StatusStrategy {
  override def statusName: String = "sleep"
  override def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    // reduceDuration
    val msg = "-" + pokemon.name + " is sleeping\n"
    val updStatusStrategy = reduceDuration(this)
    val updPokemon = if (updStatusStrategy.duration > 0) { pokemon.setStatus(updStatusStrategy) }
    else { pokemon.setStatus(new NormalState) }
    (updPokemon, msg)

  override def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy =
    copy(duration = duration - 1)

  override def clearEffect(pokemon: Pokemon): Pokemon =
    pokemon.copy(status = new StatusEffectStrategyContext)
}

case class ParalyzedState(duration: Int) extends StatusStrategy {
  override def statusName: String = "paralyzed"
  override def applyEffect(pokemon: Pokemon): (Pokemon, String) =
    // reduceDuration
    val updStatusStrategy = reduceDuration(this)
    val msg = "-" + pokemon.name + " is paralyzed\n"
    val updPokemon = if (updStatusStrategy.duration > 0) { pokemon.setStatus(updStatusStrategy) }
    else { pokemon.setStatus(new NormalState) }
    (updPokemon, msg)
  override def reduceDuration(statusStrategy: StatusStrategy): StatusStrategy =
    copy(duration = duration - 1)

  override def clearEffect(pokemon: Pokemon): Pokemon =
    pokemon.copy(status = new StatusEffectStrategyContext)
}
