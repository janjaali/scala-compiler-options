import sbt._

object publishSettings {

  val credentials = Credentials(Path.userHome / ".sbt" / ".credentials")

  val publishTo = Some {
    "sonatype" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  }
}
