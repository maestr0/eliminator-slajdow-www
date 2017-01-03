package controllers

import init.Init
import models.{Announcement, Issue, Suggestion}
import play.api.Play
import play.api.data.Form
import play.api.libs.json._

import scala.util.{Failure, Success}
import play.api.data.Forms._
import play.api.libs.ws.WS

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.mvc._
import play.api.libs.ws._
import akka.actor.ActorSystem
import akka.util.ByteString
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global


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

  def createIssue = Action.async { implicit request =>

    val issueForm = Form(
      mapping(
        "id" -> optional(text),
        "esVersion" -> text,
        "galleryUrl" -> text,
        "ua" -> text,
        "comment" -> text,
        "email" -> optional(text),
        "status" -> optional(text)
      )(Issue.apply)(Issue.unapply)
    )

    issueForm.bindFromRequest().fold(
      formWithErrors => {
        val errorMsg = formWithErrors.errors.map(_.message).mkString(", ")
        Future.successful(Redirect(routes.Application.newIssue()).flashing("error" -> s"Wystąpił błąd: ${errorMsg}"))
      },
      issue => {
        val form1 = request.body.asFormUrlEncoded.get
        if (!form1.contains("g-recaptcha-response")) {
          Future.successful(Redirect(routes.Application.newIssue()).flashing("error" -> s"Brak tokenu. Nie jesteś człowkiekiem!"))
        } else {
          val token = form1.get("g-recaptcha-response").get.head
          println(token)
          WS.url("https://www.google.com/recaptcha/api/siteverify").post(Map(
            "secret" -> Seq(Play.current.configuration.getString("recaptcha.secret").getOrElse("dev")),
            "response" -> Seq(token)
          )).map(response => {
            if ((response.json \ "success").as[Boolean]) {
              db.createIssue(issue)
              Redirect(routes.Application.issues()).flashing("success" -> "Dodano zgłoszenie")
            } else {
              Redirect(routes.Application.newIssue()).flashing("error" -> s"Bledny tokenu. Nie jesteś człowkiekiem!")
            }
          })

        }
      }
    )
  }

  def createAnnouncement(text: String, aType: String, adminToken: String) = Action {
    db.createAnnouncement(text, aType, adminToken) match {
      case Success(true) => Created
      case _ => InternalServerError("Cannot parse JSON as Issue.")
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