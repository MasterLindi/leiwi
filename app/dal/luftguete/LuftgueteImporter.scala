package dal.luftguete

import java.nio.charset.{Charset, CodingErrorAction}
import java.util.UUID

import at.fhtw.leiwi.wfsconnector.GeoTools
import com.vividsolutions.jts.geom.Geometry
import commons.utils.DateTimes
import model.{GeoFeature, GeoFeatureTyp}
import org.joda.time.{DateTime, LocalDateTime}

/**
 * Created by Christoph on 26.05.2015.
 */
class LuftgueteImporter {

  case class Luftguete(column: Int, coord: Geometry)

  val hermannkogel = new Luftguete(1, GeoTools.createPoint(16.2937778, 48.2705085))
  val hoheWarte = new Luftguete(2, GeoTools.createPoint(12.9, 34.0))
  val laaerBerg = new Luftguete(3, GeoTools.createPoint(12.9, 34.0))
  val lobau = new Luftguete(4, GeoTools.createPoint(12.9, 34.0))
  val stephansPlatz = new Luftguete(5, GeoTools.createPoint(12.9, 34.0))

  def importO3: List[GeoFeature] = {
    val inputStream = this.getClass.getResourceAsStream("/luftguete/luftguete_wien_2014_O3.csv")
    val decoder = Charset.forName("ISO-8859-1").newDecoder()
    decoder.onMalformedInput(CodingErrorAction.REPORT)

    val src = scala.io.Source.fromInputStream(inputStream)(decoder)
    val rows = src.getLines().drop(3).map(_.split(";")).toList

    var geoFeatureList : List[GeoFeature] = Nil


    rows.foreach(row =>
     geoFeatureList = geoFeatureList :::
       List[GeoFeature](
        createGeoFeature(row, hermannkogel),
        createGeoFeature(row, hoheWarte),
        createGeoFeature(row, laaerBerg),
        createGeoFeature(row, lobau),
        createGeoFeature(row, stephansPlatz))
    )

    def createGeoFeature(row: Array[String], place : Luftguete): GeoFeature = {
      new GeoFeature(UUID.randomUUID(), GeoFeatureTyp.LuftgueteO3, parseValue(row, place.column),
      place.coord, parseDate(row(0)), DateTime.now())
    }

    def parseValue(row : Array[String], columnIndex : Int): Double = {
      if ((row.length -1) < columnIndex || row(columnIndex).isEmpty) return 0.0
      row(columnIndex).replace(",", ".").toDouble
    }

    def parseDate(value : String): LocalDateTime = {
      if (value.isEmpty) return null
      DateTimes.createLocalDateTime(value.replace(",", ""))
    }
    // the rest of iter is not processed
    src.close()
    geoFeatureList
  }
}
