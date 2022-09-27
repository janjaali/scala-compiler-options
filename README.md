# scala-compiler-options

SBT plugin that adds necessary compiler options to your SBT project *(Disclaimer: the list of added compiler options is highly opinionated)*.

## Usage

Add this plugin to your project and the compiler options will be added automatically to your project:

```sbt
addSbtPlugin("com.github.janjaali" % "scala-compiler-options" % "1.3.1")
```

You can print out the compiler options that are added to your project:

```shell
sbt printCompilerOptions
```

## Supported Scala versions

* `3.2.x`
* `3.1.x`
* `3.0.x`
* `2.13.0` -> `2.13.9`

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

1. Update plugin version references in `build.sbt` and this README

2. Git commit and push latest changes (including version bump)

3. Publish plugin `sbt publish`

4. Tag version `git tag -a v1.3.1 -m "Release version 1.3.1."`

5. Create new GitHub release from tag
