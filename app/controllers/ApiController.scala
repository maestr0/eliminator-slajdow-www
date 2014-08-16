package controllers

import init.Init
import play.api.mvc._
import play.api.libs.json._
import models.{Issues, Suggestion}
import scala.concurrent.Future

object ApiController extends Controller {
  import scala.concurrent.ExecutionContext.Implicits.global
  val db = Init.dbManager

  def suggestions = Action {
    val suggestions = db.suggestions.map(s => Suggestion(s.pageUrl, s.galleryUrl, s.comment))
    Ok(Json.toJson(suggestions))
  }

  def issues = Action {
    val issues = db.issues.map(i => Issues(i.esVersion, i.galleryUrl, i.comment))
    Ok(Json.toJson(issues))
  }

  def createIssue = Action {
    Ok
  }

  def createSuggestion = Action.async(parse.json) {
    request =>
      request.body.asOpt[Suggestion] match {
        case Some(suggestion) => Future(Created(Json.toJson(suggestion)))
        case None => Future(BadRequest("Cannot parse JSON as Suggestion."))
      }
  }
}