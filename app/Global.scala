import init.Init
import play.api._
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    Init.init
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
    Future.successful(InternalServerError(
      views.html.errorPage()
    ))
  }

  override def onHandlerNotFound(request: RequestHeader) = {
    Future.successful(NotFound)
  }

  override def onBadRequest(request: RequestHeader, error: String) = {
    Future.successful(BadRequest("Bad Request: " + error))
  }
}