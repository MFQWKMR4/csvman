#!/bin/bash

# scalaのnativeビルド＋テストになるのを避けるため内容を書き換えている
sed -i -e "s/.enablePlugins(ScalaNativePlugin)/\/\/ .enablePlugins(ScalaNativePlugin)/g" build.sbt
sbt test
sed -i -e "s/\/\/ .enablePlugins(ScalaNativePlugin)/.enablePlugins(ScalaNativePlugin)/g" build.sbt

# TODO
# scala nativeのテストの適切なやり方を調べる
