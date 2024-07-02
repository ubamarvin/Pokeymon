package de.htwg.se.Pokeymon

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Pokeymon.Controller.ControllerComponent._
import de.htwg.se.Pokeymon.Model.GameComponent.GameInterface
import de.htwg.se.Pokeymon.Model.GameComponent._
import de.htwg.se.Pokeymon.Model.GameData.Setup
import de.htwg.se.Pokeymon.Model.GameData.Trainer
import de.htwg.se.Pokeymon.Model.FileIo.FileIOInterface
import de.htwg.se.Pokeymon.Model.FileIo.FileIoXml._
import de.htwg.se.Pokeymon.Model.FileIo.FileIoJson._

class PokeymonModule extends AbstractModule with ScalaModule {

  override def configure() = {
    bind[GameInterface].to[Game]
    bind[ControllerInterface].to[ControllerBaseImplementation.Controller]
    bind[GameState].toInstance(new PickPokemonState(Trainer(Vector()), Setup.pokedex, picks = 0, Setup.opponent))
    bind[Vector[GameState]].toInstance(Vector.empty[GameState])
    bind[Vector[GameState]].toInstance(Vector.empty[GameState])

    // bind[FileIOInterface].to[FileIOXml]
    bind[FileIOInterface].to[FileIoJson]

  }
}
