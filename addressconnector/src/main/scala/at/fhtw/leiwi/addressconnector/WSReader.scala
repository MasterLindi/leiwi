package at.fhtw.leiwi.addressconnector

import play.api.libs.json._

/**
 * Created by Christoph on 02.05.2015.
 */
class WSReader {
  private val baseWsURL = "http://data.wien.gv.at/daten/OGDAddressService.svc/GetAddressInfo?Address="
  private val crs = "&crs=EPSG:4326"

  def read(streetName: String, houseNr: String): WSAddress = {
    val urlBuilder = new StringBuilder(baseWsURL)
    urlBuilder.append(streetName)
    if (!houseNr.equals("0")) {
      urlBuilder.append("%20")
        .append(String.valueOf(houseNr))
    }

    urlBuilder.append(crs)
    val wsUrl = urlBuilder.toString()

    var i = 0
    try {
      val src = scala.io.Source.fromURL(wsUrl)

      val jsonString = src.getLines().toList
      // print the uid for Guest

      val json = Json.parse(jsonString.head)

      val bezirk = (json \\ "MunicipalitySubdivision").head.asInstanceOf[JsString].value

      val zip = (json \\ "Bezirk").head.asInstanceOf[JsString].value.toInt

      val coordinate = (json \\ "coordinates").seq

      val lat = coordinate.map(a => a(0)).head.toString()
      val lon = coordinate.map(a => a(1)).head.toString()

      src.close()
      return new WSAddress(bezirk, streetName, houseNr, zip, lat.toDouble, lon.toDouble)
    } catch {
      case e: Exception => null
    } finally {
      i = i + 1
    }

    null
  }
}
