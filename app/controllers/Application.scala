package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
//    val geoFeature = new GeoFeatureServiceImpl()

//    val result = geoFeature.getGeoFeature

    Ok(views.html.index("Your new application is ready."))

  }

}