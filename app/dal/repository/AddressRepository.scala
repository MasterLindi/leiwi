package dal.repository

import java.util.UUID

import model.Address
/**
 * Created by Christoph on 12.04.2015.
 */
trait AddressRepository {

  def saveOrUpdate(address : Address) : Address

  def find(id: UUID) : Option[Address]

  def get(id : UUID) : Address

}
