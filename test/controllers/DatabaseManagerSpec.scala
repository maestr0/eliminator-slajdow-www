package controllers


import info.raszewski.eliminatorslajdow.postgres.DatabaseManager
import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class DatabaseManagerSpec extends Specification {

  val dm = new DatabaseManager()

  "Computer model" should {

    "be retrieved by id" in {
      running(FakeApplication()) {
        assert(dm.suggestions.nonEmpty)
        dm.suggestions foreach println
        "Hello world" must startWith("Hello")
      }
    }
  }
}
