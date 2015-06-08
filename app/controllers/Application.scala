package controllers

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import view.model.{IndexDetailVM, IndexResultVM}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Leiwi Homepage"))
  }

  implicit val indexDetailWrites: Writes[IndexDetailVM] = (
    (JsPath \ "catalogName").write[String] and
      (JsPath \ "calculatedValue").write[Double] and
      (JsPath \ "lon").write[Double] and
      (JsPath \ "lat").write[Double] and
      (JsPath \ "distance").write[Double]
    )(unlift(IndexDetailVM.unapply))

  implicit val indexWrites: Writes[IndexResultVM] = (
    (JsPath \ "index").write[Double] and
      (JsPath \ "details").write[List[IndexDetailVM]]
    )(unlift(IndexResultVM.unapply))


  implicit val indexDetailReads: Reads[IndexDetailVM] = (
    (JsPath \ "catalogName").read[String] and
      (JsPath \ "calculatedValue").read[Double] and
      (JsPath \ "lon").read[Double] and
      (JsPath \ "lat").read[Double] and
      (JsPath \ "distance").read[Double]
    )(IndexDetailVM.apply _)

  implicit val indexReads: Reads[IndexResultVM] = (
    (JsPath \ "index").read[Double] and
      (JsPath \ "details").read[List[IndexDetailVM]]
    )(IndexResultVM.apply _)
}