# scala-compiler-options

SBT plugin that setup necessary, opinionated list of, scala compiler options for your build.

## Usage

Add this plugin to your project:

```sbt
addSbtPlugin("com.github.janjaali" % "scala-compiler-options" % "1.1.0")
```

## Supported Scala versions

* `3.0.0`
* `2.13.6`
* `2.13.5`
* `2.13.4`
* `2.13.3`
* `2.13.2`
* `2.13.1`
* `2.13.0`

## Development

This repository contains two basic test projects in [./test](./test), one for Scala 2.12.x and one for 2.13.x, that could be used to "live" test this plugin while developing.

## Publish

1. Update [CHANGELOG](./CHANGELOG.md)

2. Update plugin version references in this README and `plugins.sbt` files

3. Merge changes back into main branch

4. Publish plugin `sbt publish`

5. Tag version `git tag -a v1.1.0 -m "Release version 1.1.0."`
