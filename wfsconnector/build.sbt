name := "wfsconnector"

resolvers += "Open Source Geospatial Foundation Repository" at "http://download.osgeo.org/webdav/geotools"

libraryDependencies +=  "org.geotools" % "gt-wfs" % "13-RC1"

libraryDependencies +=  "com.google.guava" % "guava" % "16.0.1"