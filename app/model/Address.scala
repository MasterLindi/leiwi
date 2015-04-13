package model

import java.util.UUID

import anorm.SqlParser.get
import anorm._
import at.fhtw.leiwi.wfsconnector.GeoTools
import com.vividsolutions.jts.geom.Geometry
import org.joda.time.DateTime

/**
 * Created by Christoph on 12.04.2015.
 */
case class Address(id: UUID, street: String, houseNr: String, zip: Int, district: String,
                   coordinate: Geometry, updateTime: DateTime)

object Address {
  val simple = {
    get[UUID]("id") ~
      get[String]("street") ~
      get[String]("houseNr") ~
      get[Int]("zip") ~
      get[String]("district") ~
      get[String]("coordinate") ~
      get[DateTime]("updateTime") map {
      case t_id ~ street ~ houseNr ~ zip ~ district ~ coordinate ~ updateTime
      => Address(t_id, street, houseNr, zip, district, GeoTools.parseWktGeometryString(coordinate), updateTime)
    }
  }
}
//  implicit def rowToFloat: Column[PGgeometry] = Column.nonNull { (value, meta) =>
//    val MetaDataItem(qualified, nullable, clazz) = meta
//    value match {
//      case d: Float => Right(d)
//      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Float for column " + qualified))
//    }
//  }

