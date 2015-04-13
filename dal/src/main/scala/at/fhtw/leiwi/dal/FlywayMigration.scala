package at.fhtw.leiwi.dal

import org.flywaydb.core.Flyway

class FlywayMigration {

  def migrate(): Unit ={
    val flyway = new Flyway();
    flyway.setDataSource("jdbc:postgresql://vbpostgres:5432/leiwi", "leiwi", "leiwi");
    flyway.clean();
    flyway.migrate();
  }
}
