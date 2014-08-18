package info.raszewski.eliminatorslajdow.postgres

import com.jolbox.bonecp.BoneCPDataSource
import com.typesafe.config.Config

import scala.slick.driver.PostgresDriver.simple._
import models.{Issue, Suggestion}
import info.raszewski.eliminatorslajdow.postgres.Tables.{IssuesRow, SuggestionsRow}
import java.sql.Timestamp
import scala.util.{Properties, Try}
import org.apache.commons.lang3.StringEscapeUtils.escapeHtml4
import info.raszewski.eliminatorslajdow.emails.EmailSender
import scala.concurrent._
import ExecutionContext.Implicits.global

class DatabaseManager(config: Config) {
  val ds = new BoneCPDataSource
  lazy val db = {
    lazy val rdsJdbcConnectionString = Properties.envOrElse("DB_JDBC", config.getString("database-jdbc"))
    lazy val rdsDriver = Properties.envOrElse("DB_DRIVER", config.getString("database-driver"))
    lazy val rdsUser = Properties.envOrElse("DB_USER", config.getString("database-user"))
    lazy val rdsPassword = Properties.envOrElse("DB_PASSWORD", config.getString("database-password"))

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

  def createSuggestion(suggestion: Suggestion): Try[SuggestionsRow] = {
    Try {
      db.withTransaction {
        implicit session =>

          val suggestionsRow: SuggestionsRow = SuggestionsRow(1, escapeHtml4(suggestion.pageUrl), escapeHtml4(suggestion.galleryUrl), escapeHtml4(suggestion.comment), escapeHtml4(suggestion.email), "NOWY", new Timestamp(System.currentTimeMillis()), None)
          Tables.Suggestions += suggestionsRow
          future {
            EmailSender.send("ES - Sugestia", suggestionsRow.toString)
          }
          suggestionsRow
      }
    }
  }

  def createIssue(issue: Issue): Try[IssuesRow] = {
    Try {
      db.withTransaction {
        implicit session =>
          val issuesRow: IssuesRow = IssuesRow(1, escapeHtml4(issue.esVersion), escapeHtml4(issue.galleryUrl), escapeHtml4(issue.comment), escapeHtml4(issue.email), "NOWY", new Timestamp(System.currentTimeMillis()), None)
          Tables.Issues += issuesRow
          future {
            EmailSender.send("ES - Problem", issuesRow.toString)
          }
          issuesRow
      }
    }
  }
}
