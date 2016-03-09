package dal.repository

import java.util.{Locale, UUID}

import anorm._
import at.fhtw.leiwi.wfsconnector.GeoTools
import com.vividsolutions.jts.geom.Geometry
import model.Address
import org.postgresql.util.PGobject
import play.api.Play.current
import play.api.db.DB

import scala.language.postfixOps

/**
 * Created by Christoph on 12.04.2015.
 */
class AddressRepositoryImpl extends AddressRepository {
  def findNearestAddress(point: Geometry) : Address = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address where ST_Distance(coordinate,ST_GeomFromEWKT({coordinate})) <= " +
      "(Select Min(ST_Distance(coordinate,ST_GeomFromEWKT({coordinate}))) from Address where zip <> -1);")
    DB.withConnection { implicit connection =>
      val result: List[Address] = sqlQuery
            .on('coordinate -> GeoTools.createWktGeometryString(point))
        .as(Address.simple*)
      return result.head;
    }
  }

  def findByName(term: String) : List[Address] = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address where lower(street) like {streetName} and zip <> -1 order by street, houseNr ::int LIMIT 50;")
    DB.withConnection { implicit connection =>
      val result: List[Address] = sqlQuery
        .on('streetName -> (term.toLowerCase(Locale.GERMAN) + "%"))
        .as(Address.simple *)
      return result;
    }
  }


  override def findAll(): List[Address] = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address;")
    DB.withConnection { implicit connection =>
      val result: List[Address] = sqlQuery
        .as(Address.simple *)
      return result;
    }
  }

  override def findAllStreets(): List[Address] = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address where zip = -1;")
    DB.withConnection { implicit connection =>
      val result: List[Address] = sqlQuery
        .as(Address.simple *)
      return result;
    }
  }

  override def update(address: Address): Boolean = {
    val result = DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(address.id)
      SQL("update Address set street = {street}, houseNr = {houseNr}, zip = {zip}, " +
        "district = {district} , coordinate = ST_GeomFromEWKT({coordinate}), updateTime = {updateTime} " +
        "where id = {id}")
        .on('id -> anorm.Object(idObject), 'street -> address.street,
          'houseNr -> address.houseNr, 'zip -> address.zip, 'district -> address.district,
          'coordinate -> GeoTools.createWktGeometryString(address.coordinate),
          'updateTime -> address.updateTime).execute()
    }
    result
  }

  override def save(address: Address): Boolean = {
    val result = DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(address.id)
      SQL("insert into Address(id, street, houseNr, zip, district, coordinate, updateTime) " +
        "values ({id}, {street}, {houseNr}, {zip}, {district}, ST_GeomFromEWKT({coordinate}), {updateTime})")
        .on('id -> anorm.Object(idObject), 'street -> address.street,
          'houseNr -> address.houseNr, 'zip -> address.zip, 'district -> address.district,
          'coordinate -> GeoTools.createWktGeometryString(address.coordinate),
          'updateTime -> address.updateTime).execute()

    }
    result
  }

  private[AddressRepositoryImpl] def createId(id: UUID): PGobject = {
    val idObject = new PGobject()
    idObject.setType("uuid")
    idObject.setValue(id.toString)
    idObject
  }

  override def get(id: UUID): Address = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address where id = {id};")
    DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(id)
      val result: List[Address] = sqlQuery
        .on("id" -> anorm.Object(idObject))
        .as(Address.simple *)
      return result.head;
    }
  }

  override def find(id: UUID): Option[Address] = {
    val sqlQuery = SQL("select id, street, houseNr, zip, district, ST_AsEWKT(coordinate) as coordinate, " +
      "updateTime from Address where id = {id};")
    DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(id)
      val result: List[Address] = sqlQuery
        .on('id -> anorm.Object(idObject))
        .as(Address.simple *)
      if (result.isEmpty) {
        return None
      }
      Option(result.head) orElse None
    }
  }

  override def remove(id: UUID): Unit = {
    val sqlQuery = SQL("delete * from Address where id = {id};")
    DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(id)
      sqlQuery.on('id -> anorm.Object(idObject)).execute()
    }
  }

  override def removeAll(): Unit = {
    val sqlQuery = SQL("delete from Address;")
    DB.withConnection { implicit connection =>
      sqlQuery.execute()
    }
  }
}
