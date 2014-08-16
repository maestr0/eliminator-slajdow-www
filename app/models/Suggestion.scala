package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Suggestion(pageUrl: String, galleryUrl: String, comment: String)

object Suggestion {
  final val pageUrl = "pageUrl"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"

  implicit val alertReads: Reads[Suggestion] = (
    (__ \ pageUrl).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ comment).read[String]
    )(Suggestion.apply _)

  implicit val alertWrites: Writes[Suggestion] = (
    (__ \ pageUrl).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ comment).write[String]
    )(unlift(Suggestion.unapply))
}