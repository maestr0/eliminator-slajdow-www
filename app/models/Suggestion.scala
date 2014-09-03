package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Suggestion(id: Option[String], pageUrl: String, galleryUrl: String, userAgent: String, comment: String, email: String, status: Option[String])

object Suggestion {
  final val pageUrl = "pageUrl"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"
  final val email = "email"
  final val status = "status"
  final val userAgent = "ua"
  final val id = "id"

  implicit val alertReads: Reads[Suggestion] = (
    (__ \ id).readNullable[String] and
      (__ \ pageUrl).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ userAgent).read[String] and
      (__ \ comment).read[String] and
      (__ \ email).read[String] and
      (__ \ status).readNullable[String]
    )(Suggestion.apply _)

  implicit val alertWrites: Writes[Suggestion] = (
    (__ \ id).writeNullable[String] and
      (__ \ pageUrl).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ userAgent).write[String] and
      (__ \ comment).write[String] and
      (__ \ email).write[String] and
      (__ \ status).writeNullable[String]
    )(unlift(Suggestion.unapply))
}