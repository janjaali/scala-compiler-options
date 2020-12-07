import sbt._

object publishSettings {

  val credentials = Credentials(Path.userHome / ".sbt" / ".credentials")

  // val publishTo = Some("artifacts" at "https://host:port/artifacts/maven/v1")
  val publishTo = None
}
