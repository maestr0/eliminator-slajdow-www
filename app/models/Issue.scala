package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Issue(esVersion: String, galleryUrl: String, comment: String)

object Issue {
  final val esVersion = "esVersion"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"

  implicit val alertReads: Reads[Issue] = (
    (__ \ esVersion).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ comment).read[String]
    )(Issue.apply _)

  implicit val alertWrites: Writes[Issue] = (
    (__ \ esVersion).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ comment).write[String]
    )(unlift(Issue.unapply))
}