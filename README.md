# scala-compiler-options

SBT plugin that adds necessary compiler options to your SBT project *(Disclaimer: the list of added compiler options is highly opinionated)*.

## Usage

Add this plugin to your project and the compiler options will be added automatically to your project:

```sbt
addSbtPlugin("com.github.janjaali" % "scala-compiler-options" % "1.9.0")
```

You can print out the compiler options that are added to your project:

```shell
sbt printCompilerOptions
```

## Supported SBT versions

* SBT 1.x
* SBT 2.0.x

## Supported Scala versions

* Scala 2.13.x
* Scala 3.x
  * Scala 3.0.x
  * Scala 3.1.x
  * Scala 3.2.x
  * Scala 3.3.x
  * Scala 3.4.x
  * Scala 3.5.x
  * Scala 3.6.x
  * Scala 3.7.x
  * Scala 3.8.x
  * Scala 3.9.x
  * Scala 3.10.x

## Development

This repository contains test projects in [./test](./test), one for each minor Scala version. The test projects are used to "integration" test this plugin while developing. [./test/sbt_2_0](./test/sbt_2_0) verifies the plugin against SBT 2.0.x, which requires JDK 17+.

The plugin is cross-built for SBT 1.x (Scala 2.12) and SBT 2.x (Scala 3). Use `sbt +compile` and `sbt +publishLocal` to build and publish both variants.

### Organize Imports

Organize imports by using [liancheng/scalafix-organize-imports](https://github.com/liancheng/scalafix-organize-imports):

```shell
sbt scalaFixAll
```

### Format

Format source code by using [scalameta/scalafmt](https://github.com/scalameta/scalafmt):

```shell
sbt scalafmtAll
```

## Publish

1. Update plugin version in `build.sbt` and this README file.

2. Stage artifacts for SBT 1.x and 2.x `sbt +publishSigned`.

3. Upload staged artifact `sbt sonaUpload`

4. Publish uploaded artifact <https://central.sonatype.com/publishing/deployments>.

5. Tag version `git tag -a v1.8.0 -m "Release version 1.8.0."`.

6. Push tags `git push --tags`.

7. Create new GitHub release from tag.
