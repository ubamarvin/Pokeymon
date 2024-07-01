package de.htwg.se.Pokeymon.Model.FileIo

import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface

trait FileIOInterface {

  def load: GameInterface
  def save(game: GameInterface): Unit

}
