package info.raszewski.eliminatorslajdow.postgres

import com.jolbox.bonecp.BoneCPDataSource
import com.typesafe.config.Config

import scala.slick.driver.PostgresDriver.simple._
import models.{Issue, Suggestion}
import info.raszewski.eliminatorslajdow.postgres.Tables.{IssuesRow, SuggestionsRow}
import java.sql.Timestamp
import scala.util.Try

class DatabaseManager(config: Config) {
  val ds = new BoneCPDataSource
  lazy val db = {
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
    db.withTransaction {
      implicit session =>
        Tables.Suggestions.sortBy(_.createdAt).filter(_.deletedAt.isEmpty).list.reverse
    }
  }

  def issues = {
    db.withTransaction {
      implicit session =>
        Tables.Issues.sortBy(_.createdAt).filter(_.deletedAt.isEmpty).list.reverse
    }
  }

  def createSuggestion(suggestion: Suggestion) = {
    Try {
      db.withTransaction {
        implicit session =>
          Tables.Suggestions += SuggestionsRow(1, suggestion.pageUrl, suggestion.galleryUrl, suggestion.comment, new Timestamp(System.currentTimeMillis()), None)
      }
    }
  }

  def createIssue(issue: Issue) = {
    Try {
      db.withTransaction {
        implicit session =>
          Tables.Issues += IssuesRow(1, issue.esVersion, issue.galleryUrl, issue.comment, new Timestamp(System.currentTimeMillis()), None)
      }
    }
  }
}
