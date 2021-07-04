package com.github.janjaali.sbt.plugins

import sbt.Keys.{scalaVersion, scalacOptions}
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

  private val scala213addedCompilerOptions = Seq(
    "-Xlint:deprecation",                // Emit warning and location for usages of deprecated APIs.
    "-Wunused:imports",                  // Warn if an import selector is not referenced.
    "-Wdead-code",                       // Warn when dead code is identified.
    "-Wextra-implicit",                  // Warn when more than one implicit parameter section is defined.
    "-Wnumeric-widen",                   // Warn when numerics are widened.
    "-Wunused:imports",                  // Warn if an import selector is not referenced.
    "-Wunused:locals",                   // Warn if a local definition is unused.
    "-Wunused:params",                   // Warn if a value parameter is unused.
    "-Wunused:patvars",                  // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates",                 // Warn if a private member is unused.
    "-Wvalue-discard"                    // Warn when non-Unit expression results are unused.
    )

  private val scala213removedCompilerOptions = Seq(
    "-deprecation",                      // https://github.com/scala/scala/pull/7714
    "-Yno-adapted-args",                 // https://github.com/scala/bug/issues/11110
    "-Xlint:by-name-right-associative",
    "-Xlint:unsound-match",
    "-Ypartial-unification",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit"
  )

  private val scala2133removedCompilerOptions = Seq(
    "-Xlint:nullary-override"
  )

  private val scala2134addedCompilerOptions = Seq(
    "-Xlint:strict-unsealed-patmat"      // Warn when a pattern match on an unsealed type may not be exhaustive
  )

  private val scala2136addedCompilerOptions = Seq(
    "-Vimplicits", // makes the compiler print implicit resolution chains when no implicit value can be found
    "-Vtype-diffs" // turns type error messages (found: X, required: Y) into colored diffs between the two types
  )
  // format: on

  override def trigger: PluginTrigger = AllRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {

    Seq(
      scalacOptions ++= (
        if (scalaVersion.value.startsWith("2.13")) {
          val scala213Options = baseOptions.diff(
            scala213removedCompilerOptions
          ) ++ scala213addedCompilerOptions

          if (scalaVersion.value == "2.13.3") {
            scala213Options.diff(scala2133removedCompilerOptions)
          } else if (scalaVersion.value == "2.13.4") {
            scala213Options.diff(
              scala2133removedCompilerOptions
            ) ++ scala2134addedCompilerOptions
          } else if (scalaVersion.value == "2.13.6") {
            scala213Options
              .diff(
                scala2133removedCompilerOptions
              ) ++
              scala2134addedCompilerOptions ++
              scala2136addedCompilerOptions
          } else {
            scala213Options
          }
        } else { // probably 2.12.x
          baseOptions
        }
      )
    )
  }
}
