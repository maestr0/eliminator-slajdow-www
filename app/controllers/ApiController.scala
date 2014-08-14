package controllers

import init.Init
import play.api.mvc._
import play.api.libs.json._

object ApiController extends Controller {

  val db = Init.dbManager

  def suggestions = Action {
    val list = db.suggestions.map(_.pageUrl)
    list foreach println
    Ok
  }

  def error = Action {
    Ok(views.html.errorPage())
  }
}