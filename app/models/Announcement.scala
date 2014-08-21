package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Announcement(id: Option[String], text: String, aType: String)

object Announcement {
  final val text = "text"
  final val aType = "type"
  final val id = "id"

  implicit val alertReads: Reads[Announcement] = (
    (__ \ id).readNullable[String] and
      (__ \ text).read[String] and
      (__ \ aType).read[String]
    )(Announcement.apply _)

  implicit val alertWrites: Writes[Announcement] = (
    (__ \ id).writeNullable[String] and
      (__ \ text).write[String] and
      (__ \ aType).write[String]
    )(unlift(Announcement.unapply))
}