package controllers

import play.api.mvc._

object Impressum extends Controller {

  def index = Action {
    Ok(views.html.impressum())
  }
}