package info.raszewski.eliminatorslajdow.postgres

import com.jolbox.bonecp.BoneCPDataSource
import com.typesafe.config.Config

import scala.slick.driver.PostgresDriver.simple._

class DatabaseManager(config: Config) {

  val ds = new BoneCPDataSource
  val db = {
    lazy val rdsJdbcConnectionString = config.getString("database-jdbc")
    lazy val rdsDriver = config.getString("database-driver")
    lazy val rdsUser = config.getString("database-user")
    lazy val rdsPassword = config.getString("database-password")

    ds.setDriverClass(rdsDriver)
    ds.setJdbcUrl(rdsJdbcConnectionString)
    ds.setPassword(rdsPassword)
    ds.setUser(rdsUser)

    val database = Database.forDataSource(ds)
    ds.getConnection.close()

    database
  }

  def suggestions = {
    db.withTransaction { implicit session =>
      Tables.Suggestions.list
    }
  }
}
