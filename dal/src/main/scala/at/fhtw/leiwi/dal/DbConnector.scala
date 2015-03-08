package at.fhtw.leiwi.dal

import java.net.URI
import java.sql.{Connection, DriverManager}

/**
 * Created by Christoph on 08.03.2015.
 */
class DbConnector {

  def getConnection(): Connection = {
    val dbUri = new URI(System.getenv("DATABASE_URL"))
    val username = dbUri.getUserInfo.split(":")(0)
    val password = dbUri.getUserInfo.split(":")(1)
    val dbUrl = s"jdbc:postgresql://${dbUri.getHost}:${dbUri.getPort}${dbUri.getPath}"
    DriverManager.getConnection(dbUrl, username, password)
  }

  def showDatabase(): Unit = {
    val connection = getConnection
    try {
      val stmt = connection.createStatement
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())")
      val rs = stmt.executeQuery("SELECT tick FROM ticks")
      var out = ""
      while (rs.next) {
        out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
      }
    } finally {
      connection.close()
    }
  }
}
