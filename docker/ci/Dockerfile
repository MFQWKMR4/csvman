FROM ubuntu:18.04

ENV SBT_VERSION 1.2.7

# Install JDK
RUN apt-get update -y \
    && apt-get install -y openjdk-8-jdk

# Install sbt
RUN apt-get install curl --assume-yes \
    && curl -L -o sbt-$SBT_VERSION.deb https://scala.jfrog.io/artifactory/debian/sbt-$SBT_VERSION.deb \
    && dpkg -i sbt-$SBT_VERSION.deb \
    && rm sbt-$SBT_VERSION.deb \
    && sbt -Dsbt.rootdir=true sbtVersion

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8
