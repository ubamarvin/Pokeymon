package de.htwg.se.Pokeymon.Model.FileIo

import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import play.api.libs.json._


trait FileIOInterface {

  def load: GameInterface
  def save(game: GameInterface): Unit

  def getJson: JsValue

  def setGameFromJson(gameJs: JsValue): GameInterface


}
