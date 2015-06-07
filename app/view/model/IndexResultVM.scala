package view.model

/**
 * Created by cli on 01/06/15.
 */
case class IndexResultVM(index: Double, details: List[IndexDetailVM])

case class IndexDetailVM(catalogName: String, calculatedValue: Double, lon: Double, lat: Double, distance: Double)
