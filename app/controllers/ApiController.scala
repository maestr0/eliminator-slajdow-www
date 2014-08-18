package controllers

import init.Init
import play.api.mvc._
import play.api.libs.json._
import models.{Issue, Suggestion}
import scala.util.{Failure, Success}

object ApiController extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  val db = Init.dbManager

  def suggestions = Action {
    val suggestions = db.suggestions.map(s => Suggestion(Some(s.id.toString), s.pageUrl, s.galleryUrl, s.comment, s.email, Some(s.status)))
    Ok(Json.toJson(suggestions))
  }

  def issues = Action {
    val issues = db.issues.map(i => Issue(Some(i.id.toString), i.esVersion, i.galleryUrl, i.comment, i.email, Some(i.status)))
    Ok(Json.toJson(issues))
  }

  def createIssue = Action(parse.json) {
    request =>
      request.body.asOpt[Issue] match {
        case Some(issue) =>
          if (issue.comment.isEmpty || issue.galleryUrl.isEmpty)
            InternalServerError("Proszę wypełnić wszystkie wymagane pola")
          else
            db.createIssue(issue) match {
              case Success(i) => Created(views.html.issue(i))
              case _ => InternalServerError("Cannot parse JSON as Issue.")
            }
        case None => BadRequest("Cannot parse JSON as Issue.")
      }
  }

  def createSuggestion = Action(parse.json) {
    request =>
      request.body.asOpt[Suggestion] match {
        case Some(suggestion) =>
          if (suggestion.pageUrl.isEmpty || suggestion.galleryUrl.isEmpty)
            InternalServerError("Proszę wypełnić wszystkie wymagane pola")
          else
            db.createSuggestion(suggestion) match {
              case Success(s) => Created(views.html.suggestion(s))
              case _ => InternalServerError("Cannot parse JSON as Suggestion.")
            }
        case None => BadRequest("Cannot parse JSON as Suggestion.")
      }
  }

  def deleteIssue(id: String, token: String) = Action {
    db.deleteIssue(id, token) match {
      case Success(true) => NoContent
      case Success(false) => NotFound
      case Failure(t) => InternalServerError(t.getMessage)
    }
  }

  def deleteSuggestion(id: String, token: String) = Action {
    db.deleteSuggestion(id, token) match {
      case Success(true) => NoContent
      case Success(false) => NotFound
      case Failure(t) => InternalServerError(t.getMessage)
    }
  }

}