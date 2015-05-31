package business.service

import at.fhtw.leiwi.wfsconnector.GeoTools
import dal.repository.AddressRepositoryImpl
import view.model.{AddressVM, CoordinateVM}

/**
 * Created by Christoph on 12.04.2015.
 */
class AddressServiceImpl extends AddressService{
  def findNearestAddress(coord: CoordinateVM) : AddressVM = {
    val addressRepository = new AddressRepositoryImpl()
    val point = GeoTools.createPoint(coord.lon, coord.lat)
    val nearestAddress = addressRepository.findNearestAddress(point)
    new AddressVM(nearestAddress.id,nearestAddress.street,nearestAddress.houseNr, nearestAddress.zip,
      nearestAddress.district, GeoTools.getLongitude(nearestAddress.coordinate),
      GeoTools.getLattitude(nearestAddress.coordinate))
  }

  def findStreetByName(term: String) : List[AddressVM] = {
    val addressRepository = new AddressRepositoryImpl()
    val addressVMList = addressRepository.findByName(term).map(a => new AddressVM(a.id,a.street,a.houseNr, a.zip, a.district,
      GeoTools.getLongitude(a.coordinate), GeoTools.getLattitude(a.coordinate)))
    addressVMList
  }


  override def findAllStreets(): List[AddressVM] = {
    val addressRepository = new AddressRepositoryImpl()
    val addressVMList = addressRepository.findAll().map(a => new AddressVM(a.id,a.street,a.houseNr, a.zip, a.district,
      GeoTools.getLongitude(a.coordinate), GeoTools.getLattitude(a.coordinate)))
    addressVMList
  }
}
