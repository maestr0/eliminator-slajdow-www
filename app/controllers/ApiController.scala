package controllers

import init.Init
import play.api.mvc._
import play.api.libs.json._
import models.Suggestion

object ApiController extends Controller {

  val db = Init.dbManager

  def suggestions = Action {
    val suggestions = db.suggestions.map(s => Suggestion(s.pageUrl, s.galleryUrl, s.metadata))
    Ok(Json.toJson(suggestions))
  }

  def error = Action {
    Ok(views.html.errorPage())
  }
}