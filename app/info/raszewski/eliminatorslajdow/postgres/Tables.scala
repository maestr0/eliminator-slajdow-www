package info.raszewski.eliminatorslajdow.postgres

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile

  import profile.simple._

  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Suggestions.ddl ++ Issues.ddl

  def createSchema()(implicit session: Session) = ddl.create

  def dropSchema()(implicit session: Session) = ddl.drop

  case class SuggestionsRow(id: Long, pageUrl: String, galleryUrl: String, comment: String, email: String, status: String, createdAt: java.sql.Timestamp, deletedAt: Option[java.sql.Timestamp]) {
    override def toString = s"<p>$comment</p>" +
      s"<p>$pageUrl</p>" +
      s"<p>$galleryUrl</p>" +
      s"<p>$email</p>" +
      s"<p>$createdAt</p>"
  }


  /** Table description of table alerts. Objects of this class serve as prototypes for rows in queries. */
  class Suggestions(tag: Tag) extends Table[SuggestionsRow](tag, "suggestions") {
    def * = (id, pageUrl, galleryUrl, comment, email, status, createdAt, deletedAt) <>(SuggestionsRow.tupled, SuggestionsRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, pageUrl.?, galleryUrl.?, comment.?, email.?, status.?, createdAt.?, deletedAt).shaped.<>({
      r => import r._; _1.map(_ => SuggestionsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column page_url  */
    val pageUrl: Column[String] = column[String]("page_url")
    /** Database column condition  */
    val galleryUrl: Column[String] = column[String]("gallery_url")
    /** Database column period  */
    val comment: Column[String] = column[String]("comment")
    val email: Column[String] = column[String]("email")
    val status: Column[String] = column[String]("status")
    val createdAt: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column deleted_at  */
    val deletedAt: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deleted_at")
  }

  /** Collection-like TableQuery object for table Alerts */
  lazy val Suggestions = new TableQuery(tag => new Suggestions(tag))

  case class IssuesRow(id: Long, esVersion: String, galleryUrl: String, comment: String, email: String, status: String, createdAt: java.sql.Timestamp, deletedAt: Option[java.sql.Timestamp]) {
    override def toString = s"<p>ES VERSION: $esVersion</p>" +
      s"<p>$comment</p>" +
      s"<p>$galleryUrl</p>" +
      s"<p>$email</p>" +
      s"<p>$createdAt</p>"
  }

  /** Table description of table alerts. Objects of this class serve as prototypes for rows in queries. */
  class Issues(tag: Tag) extends Table[IssuesRow](tag, "issues") {
    def * = (id, esVersion, galleryUrl, comment, email, status, createdAt, deletedAt) <>(IssuesRow.tupled, IssuesRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, esVersion.?, galleryUrl.?, comment.?, email.?, status.?, createdAt.?, deletedAt).shaped.<>({
      r => import r._; _1.map(_ => IssuesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column page_url  */
    val esVersion: Column[String] = column[String]("es_version")
    /** Database column condition  */
    val galleryUrl: Column[String] = column[String]("gallery_url")
    /** Database column period  */
    val comment: Column[String] = column[String]("comment")
    val email: Column[String] = column[String]("email")
    val status: Column[String] = column[String]("status")
    val createdAt: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column deleted_at  */
    val deletedAt: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deleted_at")
  }

  /** Collection-like TableQuery object for table Issues */
  lazy val Issues = new TableQuery(tag => new Issues(tag))

  case class AnnouncementRow(id: Long, text: String, announcementType: String, deletedAt: Option[java.sql.Timestamp])
  /** Table description of table alerts. Objects of this class serve as prototypes for rows in queries. */
  class Announcement(tag: Tag) extends Table[AnnouncementRow](tag, "announcements") {
    def * = (id, text, announcementType, deletedAt) <>(AnnouncementRow.tupled, AnnouncementRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, text.?, announcementType.?, deletedAt).shaped.<>({
      r => import r._; _1.map(_ => AnnouncementRow.tupled((_1.get, _2.get, _3.get, _4)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column page_url  */
    val text: Column[String] = column[String]("text")
    val announcementType: Column[String] = column[String]("announcement_type")
    /** Database column deleted_at  */
    val deletedAt: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deleted_at")
  }

  /** Collection-like TableQuery object for table Announcement */
  lazy val Announcement = new TableQuery(tag => new Announcement(tag))
}