package info.raszewski.eliminatorslajdow.postgres

import com.jolbox.bonecp.BoneCPDataSource

import scala.slick.driver.PostgresDriver.simple._
import models.{Issue, Suggestion}
import info.raszewski.eliminatorslajdow.postgres.Tables.{IssuesRow, SuggestionsRow}
import java.sql.Timestamp
import scala.util.Try
import org.apache.commons.lang3.StringEscapeUtils.escapeHtml4
import info.raszewski.eliminatorslajdow.emails.EmailSender
import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.Play

class DatabaseManager() {

  val ds = new BoneCPDataSource
  lazy val config = Play.current.configuration
  val AdminToken = config.getString("adminToken").getOrElse("ADMIN_TOKEN")
  lazy val db = {
    lazy val rdsJdbcConnectionString = config.getString("db.jdbc").getOrElse("")
    lazy val rdsDriver = config.getString("db.driver").getOrElse("")
    lazy val rdsUser = config.getString("db.user").getOrElse("")
    lazy val rdsPassword = config.getString("db.password").getOrElse("")

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
            EmailSender.send("ES - Sugestia", suggestionsRow.toString, suggestion.email)
          }
          suggestionsRow
      }
    }
  }

  def deleteIssue(id: String, adminToken: String): Try[Boolean] = {
    Try {
      if (AdminToken == adminToken) {
        db.withTransaction {
          implicit session =>
            Tables.Issues.filter(i =>i.id === id.toLong && i.deletedAt.isEmpty).map(s => s.deletedAt).update(Some(new Timestamp(System.currentTimeMillis()))) == 1
        }
      }
      else {
        sys.error("Podaj tokeken admina")
      }
    }
  }

  def deleteSuggestion(id: String, adminToken: String): Try[Boolean] = {
    Try {
      if (AdminToken == adminToken) {
        db.withTransaction {
          implicit session =>
            Tables.Suggestions.filter(i =>i.id === id.toLong && i.deletedAt.isEmpty).map(s => s.deletedAt).update(Some(new Timestamp(System.currentTimeMillis()))) == 1
        }
      } else {
        sys.error("Podaj tokeken admina")
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
            EmailSender.send("ES - Problem", issuesRow.toString, issue.email)
          }
          issuesRow
      }
    }
  }
}
