package de.htwg.se.Pokeymon.Util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyWordSpec with Matchers {
  "An Observable" when {
    "subscribed to by an observer" should {
      "notify the observer when updated" in {
        // Arrange
        val observable = new Observable
        val observer = new Observer {
          var updated: Boolean = false
          override def update: Unit = updated = true
        }

        // Act
        observable.add(observer)
    }

    "unsubscribed from by an observer" should {
      "not notify the observer when updated" in {
        // Arrange
        val observable = new Observable
        val observer = new Observer {
          var updated: Boolean = false
          override def update: Unit = updated = true
        }

        // Act
        observable.add(observer)
        observable.remove(observer)
      }
    }
}
  }
}