package init

import com.typesafe.config.ConfigFactory
import info.raszewski.eliminatorslajdow.postgres.DatabaseManager
import play.api.Play

object Init {
  private[this] val configuration = Play.current.configuration
  val dbConfig = ConfigFactory.load(configuration.getString("dbconfig").getOrElse("database.production.conf"))
  val dbManager = new DatabaseManager(dbConfig.getConfig("rds"))
  def init = {

  }
}
