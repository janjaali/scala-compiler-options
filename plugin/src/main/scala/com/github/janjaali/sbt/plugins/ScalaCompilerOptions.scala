package com.github.janjaali.sbt.plugins

import sbt.Keys.{scalaVersion, scalacOptions}
import sbt.util.Logger
import sbt.{AllRequirements, AutoPlugin, Def, PluginTrigger}

object ScalaCompilerOptions extends AutoPlugin {

  // format: off
  // based on https://tpolecat.github.io/2017/04/25/scalac-flags.html
  private val baseOptions = Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
  )

  private val scalaV2_13_0_addedCompilerOptions = Seq(
    "-Xlint:deprecation",                // Emit warning and location for usages of deprecated APIs.
    "-Wunused:imports",                  // Warn if an import selector is not referenced.
    "-Wdead-code",                       // Warn when dead code is identified.
    "-Wextra-implicit",                  // Warn when more than one implicit parameter section is defined.
    "-Wnumeric-widen",                   // Warn when numerics are widened.
    "-Wunused:locals",                   // Warn if a local definition is unused.
    "-Wunused:params",                   // Warn if a value parameter is unused.
    "-Wunused:patvars",                  // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates",                 // Warn if a private member is unused.
    "-Wvalue-discard"                    // Warn when non-Unit expression results are unused.
    )

  private val scalaV2_13_0_removedCompilerOptions = Seq(
    "-deprecation",                      // https://github.com/scala/scala/pull/7714
    "-Yno-adapted-args",                 // https://github.com/scala/bug/issues/11110
    "-Xlint:by-name-right-associative",
    "-Xlint:unsound-match",
    "-Ypartial-unification",
    "-Ywarn-extra-implicit",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-unused:implicits",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:locals",
    "-Ywarn-unused:params",
    "-Ywarn-unused:patvars",
    "-Ywarn-unused:privates",
    "-Ywarn-value-discard"
  )

  private val scalaV2_13_0_compilerOptions = {
    baseOptions
      .diff(scalaV2_13_0_removedCompilerOptions)
      .++(scalaV2_13_0_addedCompilerOptions)
  }

  private val scalaV2_13_3_removedCompilerOptions = Seq(
    "-Xlint:nullary-override"
  )

  private val scalaV2_13_3_compilerOptions = {
    scalaV2_13_0_compilerOptions
      .diff(scalaV2_13_3_removedCompilerOptions)
  }

  private val scalaV2_13_4_addedCompilerOptions = Seq(
    "-Xlint:strict-unsealed-patmat"      // Warn when a pattern match on an unsealed type may not be exhaustive
  )

  private val scalaV2_13_4_compilerOptions = {
    scalaV2_13_3_compilerOptions
      .++(scalaV2_13_4_addedCompilerOptions)
  }

  private val scalaV2_13_6_addedCompilerOptions = Seq(
    "-Vimplicits", // makes the compiler print implicit resolution chains when no implicit value can be found
    "-Vtype-diffs" // turns type error messages (found: X, required: Y) into colored diffs between the two types
  )

  private val scalaV2_13_6_compilerOptions = {
    scalaV2_13_4_compilerOptions
      .++(scalaV2_13_6_addedCompilerOptions)
  }

  private val scalaV2_13_9_addedCompilerOptions = Seq(
    "-Wnonunit-statement"
  )

  private val scalaV2_13_9_compilerOptions = {
    scalaV2_13_6_compilerOptions
      .++(scalaV2_13_9_addedCompilerOptions)
  }

  private val scalaV2_13_11_addedCompilerOptions = Seq(
    "-Xlint:arg-discard",       // Warn about discarded adapted arguments.
    "-Xlint:int-div-to-float",  // Warn when an integer division is converted 
                                // (widened) to floating point: `(someInt / 2): Double`.
    "-Xlint:numeric-methods",   // Warn for dubious-looking numeric methods: `42.isNaN()`.
    "-Xlint:universal-methods", // Warn for dubious-looking universal methods: `someUnit.asInstanceOf[Int]`.
  )

  private val scalaV2_13_11_compilerOptions = {
    scalaV2_13_9_compilerOptions
    .++(scalaV2_13_11_addedCompilerOptions)
  }

  private val scalaV3_0_0_removedCompilerOptions = Seq(
    "-explaintypes",                  // Renamed to '-explain-type'.
    "-Wdead-code",
    "-Wextra-implicit",
    "-Wnumeric-widen",
    "-Wunused:imports",
    "-Wunused:locals",
    "-Wunused:params",
    "-Wunused:patvars",
    "-Wunused:privates",
    "-Wvalue-discard",
    "-Vimplicits",
    "-Vtype-diffs",
    "-Xcheckinit",                    // Renamed to '-Xcheck-init'.
    "-Xlint:adapted-args",
    "-Xlint:constant",
    "-Xlint:delayedinit-select",
    "-Xlint:deprecation",             // Renamed to '-deprecation'.
    "-Xlint:doc-detached",
    "-Xlint:inaccessible",
    "-Xlint:infer-any",
    "-Xlint:missing-interpolator",
    "-Xlint:nullary-unit",
    "-Xlint:option-implicit",
    "-Xlint:package-object-classes",
    "-Xlint:poly-implicit-overload",
    "-Xlint:private-shadow",
    "-Xlint:stars-align",
    "-Xlint:strict-unsealed-patmat",
    "-Xlint:type-parameter-shadow",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen"
  )

  private val scalaV3_0_0_addedCompilerOptions = Seq(
    "-deprecation",    // Emit warning and location for usages of deprecated APIs. Renamed from '-Xlint:deprecation'.
    "-explain",        // Explain errors in more detail.
    "-explain-types",  // Explain type errors in more detail. Renamed from '-explaintypes'.
    "-print-lines",    // Show source code line numbers.
    "-Ysafe-init"      // Wrap field accessors to throw an exception on uninitialized access. Rename from '-Xcheckinit'.
  )

  private val scalaV3_0_0_compilerOptions = {
    scalaV2_13_6_compilerOptions
      .diff(scalaV3_0_0_removedCompilerOptions)
      .++(scalaV3_0_0_addedCompilerOptions)
  }

  private val scalaV3_1_0_compilerOptions = {
    scalaV3_0_0_compilerOptions :+ "-Wconf:any:verbose"
  }

  private val scalaV3_3_0_compilerOptions = {
    scalaV3_1_0_compilerOptions ++ List(
      "-Wunused:imports",   // Lints for unused imports.
      "-Wunused:privates",  // Lints for unused private members.
      "-Wunused:locals",    // Lints for unused local definitions.
      "-Wunused:explicits", // Lints for unused explicit parameters.
      "-Wunused:implicits", // Lints for unused implicit parameters.
    )
  }
  // format: on

  override def trigger: PluginTrigger = AllRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {

    Seq(
      autoImport.printCompilerOptions := {
        val compilerOption = compilerOptionsForScalaVersion(
          scalaVersion = scalaVersion.value,
          logger = sbt.Keys.streams.value.log
        )

        println(compilerOption.mkString("[\n", ",\n", "\n]"))
      },
      scalacOptions ++= compilerOptionsForScalaVersion(
        scalaVersion = scalaVersion.value,
        logger = sbt.Keys.streams.value.log
      )
    )
  }

  private def compilerOptionsForScalaVersion(
      scalaVersion: String,
      logger: Logger
  ): Seq[String] = {

    scalaVersion match {
      case version if version.startsWith("3.3.") =>
        scalaV3_3_0_compilerOptions

      case version
          if version.startsWith("3.1.") ||
            version.startsWith("3.2.") =>
        scalaV3_1_0_compilerOptions

      case version if version.startsWith("3.0.") =>
        scalaV3_0_0_compilerOptions

      case "2.13.11" =>
        scalaV2_13_11_compilerOptions

      case "2.13.9" | "2.13.10" =>
        scalaV2_13_9_compilerOptions

      case "2.13.6" | "2.13.7" | "2.13.8" =>
        scalaV2_13_6_compilerOptions

      case "2.13.4" | "2.13.5" =>
        scalaV2_13_4_compilerOptions

      case "2.13.3" =>
        scalaV2_13_3_compilerOptions

      case "2.13.0" | "2.13.1" | "2.13.2" =>
        scalaV2_13_0_compilerOptions

      case _ =>
        logger.warn(
          s"scala-compiler-options: Scala version '$scalaVersion' not supported."
        )
        Nil
    }
  }

  object autoImport {

    val printCompilerOptions = sbt.inputKey[Unit](
      "Prints out used compiler options."
    )
  }
}
