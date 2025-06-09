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

## Supported Scala versions

* `3.0.x` -> `3.7.x`
* `2.13.0` -> `2.13.15`

## Development

This repository contains test projects in [./test](./test), one for each minor Scala version. The test projects are used to "integration" test this plugin while developing.

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

2. Publish plugin `sbt publishSigned`.

3. Tag version `git tag -a v1.8.0 -m "Release version 1.8.0."`.

4. Push tags `git push --tags`.

5. Create new GitHub release from tag.
