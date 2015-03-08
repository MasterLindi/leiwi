name := "dal"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"


flywayUrl := "jdbc:postgresql:vbpostgres:5432/leiwi"

flywayUser := "leiwi"

flywayPassword := "leiwi"

flywayEncoding := "UTF-8"

flywayCleanOnValidationError := true

flywayValidateOnMigrate := true