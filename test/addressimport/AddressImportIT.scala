package addressimport

import java.util.UUID

import at.fhtw.leiwi.addressconnector._
import at.fhtw.leiwi.wfsconnector.GeoTools
import dal.repository.{AddressRepositoryImpl, WithTestServer}
import model.Address
import org.joda.time.DateTime
import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * Created by Christoph on 02.05.2015.
 */
class AddressImportIT extends FlatSpec {
  "All Streets from Vienna" should "be persited to db" in {
    WithTestServer.start()
    val csvService = new CSVReader()

    val streetList = csvService.readCsv
    val geometry = GeoTools.createPoint(12.0, 32.4)


    val addressRepo = new AddressRepositoryImpl()
    addressRepo.removeAll()
    var streetCount = 0

    streetList.foreach { s =>
      val uuid = UUID.randomUUID()
      val address = new Address(uuid, s, "0", -1, "0", geometry, DateTime.now())
      addressRepo.save(address)
      streetCount += 1
    }
    println(String.format("%s Straßen wurden eingefügt!", String.valueOf(streetCount)))
    WithTestServer.stop()
  }

  "All Addresses from Vienna" should "be updated to db" in {
    WithTestServer.start()
    val wsService = new WSReader()
    val addressRepo = new AddressRepositoryImpl()

    val streetList = addressRepo.findAllStreets()

    var countAddressses = 0

    val futureList = for(street <- streetList) yield Future[Int]{
      loopTroughHouseNumbers(wsService, addressRepo, street)
    }

    val waitingList = Future sequence futureList

    waitingList onComplete {
      case Success(insertedAddresses) => countAddressses
      case Failure(exp) => println("Fehler beim Einfügen der Addressdaten: " + exp.getMessage)
    }

    val results = Await result (waitingList, Duration.Inf)
    println(results)

    println(String.format("%s Addressen wurden eingefügt!", String.valueOf(countAddressses)))
    WithTestServer.stop()
  }

  private def loopTroughHouseNumbers(wsService: WSReader, addressRepo: AddressRepositoryImpl, a: Address): Int = {
    var countAddresses = 0
    println(String.format("Import Addresses for street: %s; Time: %s", a.street, DateTime.now()))
    for (i <- 0 to 622) {
      val wsAddress = wsService.read(a.street, String.valueOf(i))
      if (wsAddress != null) {
        var newAddress = new Address(UUID.randomUUID(), a.street, wsAddress.houseNr, wsAddress.zip, wsAddress.district,
          GeoTools.createPoint(wsAddress.lat, wsAddress.lon), DateTime.now())
        addressRepo.save(newAddress)
        countAddresses += 1
      }
    }
    countAddresses
  }
}
