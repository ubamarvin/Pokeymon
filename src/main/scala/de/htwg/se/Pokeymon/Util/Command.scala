package de.htwg.se.Pokeymon.Util

trait Command {

  def executeStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}
