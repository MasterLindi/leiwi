package at.fhtw.leiwi.addressconnector

import java.nio.charset.{Charset, CodingErrorAction}

class CSVReader {


  def readCsv: List[String] ={
    val inputStream = this.getClass.getResourceAsStream("/streetfile/streets.csv")
    val decoder = Charset.forName("ISO-8859-1").newDecoder()
    decoder.onMalformedInput(CodingErrorAction.REPORT)

    val src = scala.io.Source.fromInputStream(inputStream)(decoder)
    val iter = src.getLines().drop(1).map(_.split(","))
    // print the uid for Guest

    val streetList = iter.map(a => a(2)).toList
    // the rest of iter is not processed
    src.close()
    return streetList
  }
}
