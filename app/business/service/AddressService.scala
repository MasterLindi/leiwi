package business.service

import view.model.{AddressVM, CoordinateVM}

/**
 * Created by Christoph on 12.04.2015.
 */
trait AddressService {
  def findAllStreets() : List[AddressVM]

  def findStreetByName(term: String) : List[AddressVM]

  def findNearestAddress(coord: CoordinateVM) : AddressVM
}
