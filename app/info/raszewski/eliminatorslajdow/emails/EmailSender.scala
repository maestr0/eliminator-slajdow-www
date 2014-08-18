package info.raszewski.eliminatorslajdow.emails

import com.github.sebrichards.postmark.{PostmarkSuccess, PostmarkError, PostmarkMessage, PostmarkClient}
import play.api.{Play, Logger}

object EmailSender {

  def send(subject: String, content: String, sendFrom: String) = {


    val postmarkApiKey: String = Play.current.configuration.getString("postmark.apikey").getOrElse("")
    Logger.info(s"Postmark Api Key: $postmarkApiKey")

    val client = new PostmarkClient(postmarkApiKey)

    val message = PostmarkMessage(
      // Required fields
      To = "Pawel Raszewski <pawel@raszewski.info>",
      From = "ES - Srona Domowa <pawel@raszewski.info>",
      Subject = subject,
      HtmlBody = Some(content),
      // Optional mail fields
      ReplyTo = Some(sendFrom)
    )

    val result: Either[PostmarkError, PostmarkSuccess] = client.send(message)

    client.destroy
  }
}
