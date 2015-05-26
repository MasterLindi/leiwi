package model

import java.util.UUID

import com.vividsolutions.jts.geom.Geometry
import org.joda.time.{DateTime, LocalDateTime}

/**
 * Created by Christoph on 26.05.2015.
 */
case class GeoFeature(id: UUID, description: String, value: Double, coordinate: Geometry, measureDate :LocalDateTime, updateTime : DateTime)

object GeoFeatureTyp {
  val LuftgueteO3 = "Luftgüte O3"
}
