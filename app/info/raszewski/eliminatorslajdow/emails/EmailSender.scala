package info.raszewski.eliminatorslajdow.emails

import com.github.sebrichards.postmark.{PostmarkSuccess, PostmarkError, PostmarkMessage, PostmarkClient}

object EmailSender {

  def send(subject: String, content: String) = {


    val client = new PostmarkClient(scala.util.Properties.envOrElse("POSTMARK_API_KEY", ""))

    val message = PostmarkMessage(
      // Required fields
      To = "Recipient <pawel@raszewski.info>",
      From = "Postmark Sender <pawel@raszewski.info>",
      Subject = subject,
      HtmlBody = Some(content),
      // Optional mail fields
      ReplyTo = Some("pawel@raszewski.info")
    )

    val result: Either[PostmarkError, PostmarkSuccess] = client.send(message)

    client.destroy
  }
}
