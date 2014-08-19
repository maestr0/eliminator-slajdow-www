package controllers

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApiControllerSpec extends Specification {

  "API" should {
    "return list of suggestions" in new WithApplication {
      val api = route(FakeRequest(GET, "/api/suggestions")).get
      status(api) must equalTo(OK)
      contentType(api) must beSome.which(_ == "application/json")
    }
    "return list of issues" in new WithApplication {
      val api = route(FakeRequest(GET, "/api/issues")).get
      status(api) must equalTo(OK)
      contentType(api) must beSome.which(_ == "application/json")
    }
  }
}
