package business.service

import at.fhtw.leiwi.wfsconnector.GeoTools
import dal.repository.AddressRepositoryImpl
import view.model.AddressVM

/**
 * Created by Christoph on 12.04.2015.
 */
class AddressServiceImpl extends AddressService{

  override def findAllStreets(): List[AddressVM] = {
    val addressRepository = new AddressRepositoryImpl()
    val addressVMList = addressRepository.findAll().map(a => new AddressVM(a.id,a.street,a.houseNr, a.zip, a.district,
      GeoTools.getLongitude(a.coordinate), GeoTools.getLattitude(a.coordinate)))
    addressVMList
  }
}
