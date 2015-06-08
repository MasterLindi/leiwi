package controllers

import java.util.UUID

import business.service.{AddressServiceImpl, IndexCalculationServiceImpl}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import view.model._

/**
 * Created by Christoph on 25.05.2015.
 */
object Address extends Controller {

  implicit val addressWrites: Writes[AddressVM] = (
    (JsPath \ "id").write[UUID] and
      (JsPath \ "street").write[String] and
      (JsPath \ "houseNr").write[String] and
      (JsPath \ "zip").write[Int] and
      (JsPath \ "district").write[String] and
      (JsPath \ "lon").write[Double] and
      (JsPath \ "lat").write[Double]
    )(unlift(AddressVM.unapply))

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

  implicit val coordReads: Reads[CoordinateVM] = (
    (JsPath \ "lon").read[Double] and
      (JsPath \ "lat").read[Double]
    )(CoordinateVM.apply _)


  implicit val indexValReads: Reads[IndexVM] = (
    (JsPath \ "lon").read[Double] and
      (JsPath \ "lat").read[Double] and
      (JsPath \ "common").read[Boolean] and
      (JsPath \ "mobility").read[Boolean] and
      (JsPath \ "family").read[Boolean] and
      (JsPath \ "students").read[Boolean] and
      (JsPath \ "retired").read[Boolean]
    )(IndexVM.apply _)

  def index(term: String) = Action {

    val addressService = new AddressServiceImpl()

    val json = Json.toJson(addressService.findStreetByName(term))
    Ok(json)
  }

  def getAddress = Action(BodyParsers.parse.json) { request =>
    val coordResult = request.body.validate[CoordinateVM]
    coordResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      coord => {
        val addressService = new AddressServiceImpl()
        val nearestAddress = addressService.findNearestAddress(coord)
        val json = Json.toJson(nearestAddress)
        Ok(json)
      }
    )

  }

  def calculateIndex() = Action(BodyParsers.parse.json) { request =>
    val input = request.body.validate[IndexVM]
    input.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      indexVM => {
        val calcService = new IndexCalculationServiceImpl()
        val calculatedIndexValues = calcService.calculateIndex(indexVM)
        val json = Json.toJson(calculatedIndexValues)
        Ok(json)
      }
    )
  }
}
