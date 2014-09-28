package controllers

import play.api.Play
import play.api.Play.current
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request: RequestHeader =>
    Ok(views.html.index())
  }

  def sourceCode = Action { implicit request =>
    Ok(views.html.index("sourceCode"))
  }

  def about = Action { implicit request =>
    Ok(views.html.index("about"))
  }

  def donation = Action { implicit request =>
    Ok(views.html.index("donation"))
  }

  def admin = Action { implicit request =>
    Ok(views.html.index("donation"))
  }

  def safariExtension = Action { implicit request =>
    val app = Play.application
    val file = Play.application.getFile("public/safari/eliminator-slajdow.safariextz")
    val source = scala.io.Source.fromFile(file)(scala.io.Codec.ISO8859)
    val byteArray = source.map(_.toByte).toArray
    source.close()
    Ok(byteArray).as("application/octet-stream")
  }

}