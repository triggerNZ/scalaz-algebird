scalaVersion := "2.11.7"

libraryDependencies += "com.twitter" %% "algebird-core" % "0.11.0"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.4"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.4" % "test")

scalacOptions ++= Seq("-feature", "-language:higherKinds")

scalacOptions in Test ++= Seq("-Yrangepos")
