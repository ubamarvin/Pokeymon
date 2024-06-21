package de.htwg.se.Pokeymon

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Pokeymon.controller.controllerComponent._
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.GameComponent

class PokemonModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 9

  def configure() = {
    bindConstant().annotatedWith(Names.named("StandardGame")).to(StandardGame)
    bind[GameInterface].to[Game]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
  }
}
