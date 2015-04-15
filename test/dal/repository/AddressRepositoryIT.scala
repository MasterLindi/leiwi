package dal.repository

import java.util.UUID

import at.fhtw.leiwi.wfsconnector.GeoTools
import model.Address
import org.joda.time.DateTime
import org.scalatest.FlatSpec

/**
 * Created by Christoph on 13.04.2015.
 */
class AddressRepositoryIT extends FlatSpec{

  "An Address" should "be persited to db" in {
    WithTestServer.start()
    val uuid = UUID.randomUUID()
    val geometry = GeoTools.createPoint(12.0, 32.4)
    val address = new Address(uuid, "Hauptstraße", "101", 2275, "Donaustadt", geometry, new DateTime(2012,12,12,0,0))
    val addressRepo = new AddressRepositoryImpl()
    val actual = addressRepo.saveOrUpdate(address)
    assert(actual == null)
    WithTestServer.stop()
  }

  it should "be read from db" in {
    WithTestServer.start()
    val uuid = UUID.fromString("9313d05b-1a05-48b8-b5c4-11d765514c83")
    val addressRepo = new AddressRepositoryImpl()
    val actual = addressRepo.find(uuid)
    assert(actual != null)
    WithTestServer.stop()
  }
}
