package business.service

import view.model.AddressVM

/**
 * Created by Christoph on 12.04.2015.
 */
trait AddressService {
  def findAllStreets() : List[AddressVM]

}
