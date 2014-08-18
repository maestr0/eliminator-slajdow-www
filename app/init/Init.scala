package init

import com.typesafe.config.ConfigFactory
import info.raszewski.eliminatorslajdow.postgres.DatabaseManager
import play.api.Play

object Init {
  val dbManager = new DatabaseManager()
  def init = {

  }
}
