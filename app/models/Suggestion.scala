package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Suggestion(pageUrl: String, galleryUrl: String, comment: String, email: String, status: String)

object Suggestion {
  final val pageUrl = "pageUrl"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"
  final val email = "email"
  final val status = "status"

  implicit val alertReads: Reads[Suggestion] = (
    (__ \ pageUrl).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ comment).read[String] and
      (__ \ email).read[String] and
      (__ \ status).read[String]
    )(Suggestion.apply _)

  implicit val alertWrites: Writes[Suggestion] = (
    (__ \ pageUrl).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ comment).write[String] and
      (__ \ email).write[String] and
      (__ \ status).write[String]
    )(unlift(Suggestion.unapply))
}