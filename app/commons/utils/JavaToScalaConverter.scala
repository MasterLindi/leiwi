package commons.utils

import view.model.IndexDetailVM

import scala.collection.JavaConversions._
/**
 * Created by cli on 01/06/15.
 */
class JavaToScalaConverter {


  def convertList(javaList: java.util.List[IndexDetailVM]) : List[IndexDetailVM] = {
    javaList.toList
  }

}
