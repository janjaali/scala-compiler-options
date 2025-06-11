import sbt._

object publishSettings {

  // realm=Sonatype Nexus Repository Manager
  // host=central.sonatype.com
  // user=***
  // password=***
  val credentials = Credentials(Path.userHome / ".sbt" / ".credentials")
}
