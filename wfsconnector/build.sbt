name := "wfsconnector"

resolvers += "Open Source Geospatial Foundation Repository" at "http://download.osgeo.org/webdav/geotools"

libraryDependencies += "javax.media" % "jai_core" % "1.1.3" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_core/1.1.3/jai_core-1.1.3.jar"

libraryDependencies +=  "org.geotools" % "gt-wfs" % "13-RC1" exclude("javax.media", "jai_core")

libraryDependencies +=  "com.google.guava" % "guava" % "16.0.1"