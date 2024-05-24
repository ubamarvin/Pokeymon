package de.htwg.se.Pokeymon.Model

//___________Class Moves____________///
//extend with pp
//extend with type
//extend with executeMove()?
case class Move(
    name: String,
    power: Int,
    moveType: String,
    statusEffect: String = "none"
)
