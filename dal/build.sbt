name := "dal"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies += "org.flywaydb" % "flyway-core" % "3.1"

Seq(flywaySettings: _*)

flywayUrl := "jdbc:postgresql://vbpostgres:5432/leiwi"

flywayUser := "leiwi"

flywayPassword := "leiwi"

flywayEncoding := "UTF-8"

flywayCleanOnValidationError := true

flywayValidateOnMigrate := true