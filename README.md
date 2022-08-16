# scala-compiler-options

SBT plugin that setup necessary, opinionated list of, scala compiler options for your build.

## Usage

Add this plugin to your project:

```sbt
addSbtPlugin("com.github.janjaali" % "scala-compiler-options" % "1.3.0")
```

## Supported Scala versions

* `3.0.x`
* `2.13.6`
* `2.13.5`
* `2.13.4`
* `2.13.3`
* `2.13.2`
* `2.13.1`
* `2.13.0`

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

1. Update plugin version references in this README and `plugins.sbt` files

2. Merge changes back into main branch

3. Publish plugin `sbt publish`

4. Tag version `git tag -a v1.3.0 -m "Release version 1.3.0."`
