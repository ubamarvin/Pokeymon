package de.htwg.se.Pokeymon.Model

//case class Choice(choiceType: String, choiceDetail: Any)

trait Choice {
  def choiceType: String
}

case class AttackChoice(move: Option[Move]) extends Choice {
  override def choiceType: String = "attack"
}

case class ItemChoice(item: Item, targetPokemon: Pokemon) extends Choice {
  override def choiceType: String = "item"
}

case class SwitchPokemonChoice(pokemon: Option[Pokemon]) extends Choice {
  override def choiceType: String = "switch"
}
