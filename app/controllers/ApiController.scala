package controllers

import init.Init
import models.{Announcement, Issue, Suggestion}
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success}

object ApiController extends Controller {

  val db = Init.dbManager

  def suggestions = Action {
    val suggestions = db.suggestions.map(s => Suggestion(Some(s.id.toString), s.pageUrl, s.galleryUrl, s.userAgent, s.comment, s.email, Some(s.status)))
    Ok(Json.toJson(suggestions))
  }

  def issues = Action {
    val issues = db.issues.map(i => Issue(Some(i.id.toString), i.esVersion, i.galleryUrl, i.userAgent, i.comment, i.email, Some(i.status)))
    Ok(Json.toJson(issues))
  }

  def announcements = Action {
    val announcements = db.announcements.map(i => Announcement(Some(i.id.toString), i.text, i.announcementType))
    Ok(Json.toJson(announcements))
  }

  def createIssue = Action(parse.json) {
    implicit request =>
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

  def createAnnouncement(text: String, aType: String, adminToken: String) = Action {
    db.createAnnouncement(text, aType, adminToken) match {
      case Success(true) => Created
      case _ => InternalServerError("Cannot parse JSON as Issue.")
    }
  }


  def createSuggestion = Action(parse.json) {
    implicit request =>
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

  def deleteIssueWithAdminCookie(id: String) = Action { request =>
    request.cookies.get("admin") match {
      case Some(cookie) => deleteIssueFromDb(id, cookie.value)
      case None => Forbidden
    }
  }

  def deleteIssue(id: String, token: String) = Action {
    deleteIssueFromDb(id, token)
  }

  def deleteIssueFromDb(id: String, token: String): SimpleResult = {
    db.deleteIssue(id, token) match {
      case Success(true) => NoContent
      case Success(false) => NotFound
      case Failure(t) => InternalServerError(t.getMessage)
    }
  }

  def deleteAnnouncement(id: String, token: String) = Action {
    db.deleteAnnouncement(id, token) match {
      case Success(true) => NoContent
      case Success(false) => NotFound
      case Failure(t) => InternalServerError(t.getMessage)
    }
  }

  def updateIssueStatus(id: String, newStatus: String, token: String) = Action {
    db.updateIssueStatus(id, newStatus, token) match {
      case Success(true) => Ok
      case Success(false) => NotFound
      case Failure(t) => InternalServerError(t.getMessage)
    }
  }

  def updateSuggestionStatus(id: String, newStatus: String, token: String) = Action {
    db.updateSuggestionStatus(id, newStatus, token) match {
      case Success(true) => Ok
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