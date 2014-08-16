package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Issues(esVersion: String, galleryUrl: String, comment: String)

object Issues {
  final val esVersion = "esVersion"
  final val galleryUrl = "galleryUrl"
  final val comment = "comment"

  implicit val alertReads: Reads[Issues] = (
    (__ \ esVersion).read[String] and
      (__ \ galleryUrl).read[String] and
      (__ \ comment).read[String]
    )(Issues.apply _)

  implicit val alertWrites: Writes[Issues] = (
    (__ \ esVersion).write[String] and
      (__ \ galleryUrl).write[String] and
      (__ \ comment).write[String]
    )(unlift(Issues.unapply))
}