package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Issue(esVersion: String, galleryUrl: String, comment: String, email: String, status: Option[String])

object Issue {
  final val esVersion = "esVersion"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"
  final val email = "email"
  final val status = "status"

  implicit val alertReads: Reads[Issue] = (
    (__ \ esVersion).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ comment).read[String] and
      (__ \ email).read[String] and
      (__ \ status).readNullable[String]
    )(Issue.apply _)

  implicit val alertWrites: Writes[Issue] = (
    (__ \ esVersion).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ comment).write[String] and
      (__ \ email).write[String] and
      (__ \ status).writeNullable[String]
    )(unlift(Issue.unapply))
}