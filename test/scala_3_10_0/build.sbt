scalaVersion := "3.10.0"

Compile / scalacOptions ~= { options =>
  require(options.contains("-Werror"), "Missing Scala 3 warning options")
  require(options.contains("-Wunused:patvars"), "Missing pattern warnings")
  require(!options.contains("-print-lines"), "Deprecated option is enabled")
  options
}
