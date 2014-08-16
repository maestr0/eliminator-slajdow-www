package controllers

import init.Init
import play.api.mvc._
import play.api.libs.json._
import models.{Issue, Suggestion}
import scala.concurrent.Future
import scala.util.Success

object ApiController extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  val db = Init.dbManager

  def suggestions = Action {
    val suggestions = db.suggestions.map(s => Suggestion(s.pageUrl, s.galleryUrl, s.comment))
    Ok(Json.toJson(suggestions))
  }

  def issues = Action {
    val issues = db.issues.map(i => Issue(i.esVersion, i.galleryUrl, i.comment))
    Ok(Json.toJson(issues))
  }

  def createIssue = Action(parse.json) {
    request =>
      request.body.asOpt[Issue] match {
        case Some(issue) =>
          db.createIssue(issue) match {
            case Success(_) => Created(Json.toJson(issue))
            case _ => InternalServerError("Cannot parse JSON as Issue.")
          }
        case None => BadRequest("Cannot parse JSON as Issue.")
      }
  }

  def createSuggestion = Action(parse.json) {
    request =>
      request.body.asOpt[Suggestion] match {
        case Some(suggestion) =>
          db.createSuggestion(suggestion) match {
            case Success(_) => Created(Json.toJson(suggestion))
            case _ => InternalServerError("Cannot parse JSON as Suggestion.")
          }
        case None => BadRequest("Cannot parse JSON as Suggestion.")
      }
  }
}