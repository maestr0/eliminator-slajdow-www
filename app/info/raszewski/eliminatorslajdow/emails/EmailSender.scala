package info.raszewski.eliminatorslajdow.emails

import com.github.sebrichards.postmark.{PostmarkSuccess, PostmarkError, PostmarkMessage, PostmarkClient}
import info.raszewski.eliminatorslajdow.postgres.Tables
import info.raszewski.eliminatorslajdow.postgres.Tables.{IssuesRow, SuggestionsRow}
import play.api.{Play, Logger}

import scala.slick.driver.PostgresDriver

object EmailSender {
  val adminToken = Play.current.configuration.getString("adminToken").getOrElse("ADMIN_TOKEN")

  def send(subject: String, content: String, sendFrom: String) = {
    val postmarkApiKey: String = Play.current.configuration.getString("postmark.apikey").getOrElse("")
    val client = new PostmarkClient(postmarkApiKey)
    val message = PostmarkMessage(
      // Required fields
      To = "Pawel Raszewski <pawel@raszewski.info>",
      From = s"$subject <pawel@raszewski.info>",
      Subject = subject,
      HtmlBody = Some(content),
      // Optional mail fields
      ReplyTo = Some(sendFrom)
    )

    val result: Either[PostmarkError, PostmarkSuccess] = client.send(message)

    client.destroy
  }

  def sendCreateIssueNotification(issue: IssuesRow) = {
    val content = s"<p>ES VERSION: ${issue.esVersion}</p>" +
      s"<p>${issue.comment}</p>" +
      s"<p>${issue.galleryUrl}</p>" +
      s"<p>${issue.email}</p>" +
      s"<p>${issue.createdAt}</p>" +
      s"<p>${issue.userAgent}</p>" +
      s"<a href='http://eliminator-slajdow.raszewski.info/'>ES HP</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/issues/delete/${issue.id}/$adminToken'>Usun</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/issues/${issue.id}/ZAAKCEPTOWANO/$adminToken'>Status - Zaakceptowano</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/issues/${issue.id}/ODRZUCONO/$adminToken'>Status - Odrzucono</a>"

    send("ES Problem", content, issue.email.getOrElse("N/A"))
  }

  def sendCreateSuggestionNotification(suggestion: SuggestionsRow) = {
    val content = s"<p>${suggestion.comment}</p>" +
      s"<p>${suggestion.galleryUrl}</p>" +
      s"<p>${suggestion.email}</p>" +
      s"<p>${suggestion.createdAt}</p>" +
      s"<p>${suggestion.userAgent}</p>" +
      s"<a href='http://eliminator-slajdow.raszewski.info/'>ES HP</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/suggestions/delete/${suggestion.id}/$adminToken'>Usun</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/suggestions/${suggestion.id}/ZAAKCEPTOWANO/$adminToken'>Status - Zaakceptowano</a><br />" +
      s"<a href='http://eliminator-slajdow.raszewski.info/api/suggestions/${suggestion.id}/ODRZUCONO/$adminToken'>Status - Odrzucono</a>"

    send("ES Sugestia", content, suggestion.email)
  }
}
