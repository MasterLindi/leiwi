package at.fhtw.leiwi.addressconnector

class CSVReader {


  def readCsv: List[String] ={
    val src = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/streetfile/streets.csv"))
    val iter = src.getLines().drop(1).map(_.split(","))
    // print the uid for Guest

    val streetList = iter.map(a => a(2)).toList;
    // the rest of iter is not processed
    src.close()
    return streetList;
  }
}
