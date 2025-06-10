import sbt._

object publishSettings {

  val credentials = Credentials(Path.userHome / ".sbt" / ".credentials")
}
