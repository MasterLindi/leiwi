package dal.repository

import java.util.UUID

import anorm._
import at.fhtw.leiwi.wfsconnector.GeoTools
import model.Address
import org.postgresql.util.PGobject
import play.api.Play.current
import play.api.db.DB

import scala.language.postfixOps
/**
 * Created by Christoph on 12.04.2015.
 */
class AddressRepositoryImpl extends AddressRepository {
  override def saveOrUpdate(address: Address): Address = {

    val result = DB.withConnection { implicit connection =>
      val idObject: PGobject = createId(address.id)
      SQL("insert into Address(id, street, houseNr, zip, district, coordinate, updateTime) " +
        "values ({id}, {street}, {houseNr}, {zip}, {district}, ST_GeomFromEWKT({coordinate}), {updateTime})")
        .on('id -> anorm.Object(idObject), 'street -> address.street,
          'houseNr -> address.houseNr, 'zip -> address.zip, 'district -> address.district,
          'coordinate -> GeoTools.createWktGeometryString(address.coordinate),
          'updateTime -> address.updateTime).execute()
    }
//    find(address.id) match{
//      case None =>
//
//        get(address.id)
//      case Some(x) => null
//    }
    null
  }

  private[AddressRepositoryImpl] def createId(id: UUID): PGobject = {
    val idObject = new PGobject()
    idObject.setType("uuid")
    idObject.setValue(id.toString)
    idObject
  }

  override def get(id: UUID): Address = {
    val sqlQuery = SQL("select * from Address where id = {id};")
    DB.withConnection { implicit connection =>
      val result: List[Address] = sqlQuery
        .on("id" -> id)
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
      return Some(result.head);
    }
  }
}
