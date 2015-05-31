package dal.repository

import java.util.UUID

import com.vividsolutions.jts.geom.Geometry
import model.Address
/**
 * Created by Christoph on 12.04.2015.
 */
trait AddressRepository {

  def update(address : Address) : Boolean

  def save(address : Address) : Boolean

  def find(id: UUID) : Option[Address]

  def findAll() : List[Address]

  def findAllStreets() : List[Address]

  def get(id : UUID) : Address

  def remove(id : UUID) :Unit

  def removeAll() : Unit

  def findByName(term: String) : List[Address]

  def findNearestAddress(point: Geometry) : Address
}
