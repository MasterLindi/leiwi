package view.model

import java.util.UUID

/**
 * Created by Christoph on 25.05.2015.
 */
case class AddressVM (id: UUID, street: String, houseNr: String, zip: Int, district: String,
                     lon: Double, lat: Double)
