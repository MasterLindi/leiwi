package controllers

import java.util.UUID

import business.service.AddressServiceImpl
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import view.model.{AddressVM, CoordinateVM}

/**
 * Created by Christoph on 25.05.2015.
 */
object Address extends Controller {

  implicit val addressWrites: Writes[AddressVM] =(
    (JsPath \ "id").write[UUID] and
      (JsPath \ "street").write[String] and
      (JsPath \ "houseNr").write[String] and
      (JsPath \ "zip").write[Int] and
      (JsPath \ "district").write[String] and
      (JsPath \ "lon").write[Double] and
      (JsPath \ "lat").write[Double]
    )(unlift(AddressVM.unapply))

  implicit val coordReads: Reads[CoordinateVM] = (
    (JsPath \ "lat").read[Double] and
    (JsPath \ "lon").read[Double]
  )(CoordinateVM.apply _)

  def index(term: String) = Action {

    val addressService = new AddressServiceImpl()

    val json = Json.toJson(addressService.findStreetByName(term))
    Ok(json)
  }

  def getAddress = Action(BodyParsers.parse.json) { request =>
    val coordResult = request.body.validate[CoordinateVM]
    coordResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
      },
      coord => {
        val addressService = new AddressServiceImpl()
        val nearestAddress = addressService.findNearestAddress(coord);
        val json = Json.toJson(nearestAddress)
        Ok(json)
      }
    )

  }
}
