import java.sql.Timestamp

import com.typesafe.config.ConfigFactory
import info.raszewski.eliminatorslajdow.postgres.Tables.{SuggestionsRow, _}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.slick.driver.H2Driver.simple._

class DatabaseSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfterAll {

  val config = ConfigFactory.load("database.production.conf")
  val rdsConfig = config.resolve().getConfig("rds")

  lazy val rdsJdbcConnectionString = rdsConfig.getString("database-jdbc")
  lazy val rdsDriver = rdsConfig.getString("database-driver")
  lazy val rdsUser = rdsConfig.getString("database-user")
  lazy val rdsPassword = rdsConfig.getString("database-password")

  println(rdsConfig)

  val database = Database.forURL(rdsJdbcConnectionString, user = rdsUser, password = rdsPassword, driver = rdsDriver)
  implicit val session: Session = database.createSession()

  def populateDatabase(): Unit = {
    val timestamp = new Timestamp(System.currentTimeMillis())
    Suggestions += SuggestionsRow(1, "test1.com", "gallery.test.com", "metadata", timestamp, None)
    Suggestions += SuggestionsRow(1, "test2.com", "gallery.test.com", "metadata", timestamp, None)
    Suggestions += SuggestionsRow(1, "test3.com", "gallery.test.com", "metadata", timestamp, None)
  }

  override def beforeAll() = {
    createSchema()
    populateDatabase()
  }

  override def afterAll() = {
    dropSchema()
    session.close()
  }

  it should "work" in {
    Suggestions.foreach(println)
  }
}
