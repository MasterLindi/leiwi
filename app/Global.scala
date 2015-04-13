//import at.fhtw.leiwi.dal.{FlywayMigration, DbConnector}
import play.api._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
//    val flyway = new FlywayMigration();
//    flyway.migrate();
    Logger.info("Db was migrated")
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}