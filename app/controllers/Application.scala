package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def sourceCode = Action {
    Ok(views.html.index("sourceCode"))
  }

  def about = Action {
    Ok(views.html.index("about"))
  }

  def donation = Action {
    Ok(views.html.index("donation"))
  }

}