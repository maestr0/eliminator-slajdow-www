package controllers

import com.typesafe.config.ConfigFactory
import info.raszewski.eliminatorslajdow.postgres.DatabaseManager
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class DatabaseManagerSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfterAll {

  val config = ConfigFactory.load("database.production.conf")
  val rdsConfig = config.resolve().getConfig("rds")

  val dm = new DatabaseManager(rdsConfig)

  def populateDatabase(): Unit = {

  }

  override def beforeAll() = {

  }

  override def afterAll() = {

  }

  it should "work" in {
    assert(dm.suggestions.nonEmpty)
    dm.suggestions foreach println
  }
}
