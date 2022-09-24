FROM ubuntu:20.04

RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install curl zip unzip -y

# Install sdkman, java11, sbt
RUN curl -s "https://get.sdkman.io" | bash
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh \
&& sdk install java 11.0.16-librca \
&& curl -L -o sbt-1.2.7.deb https://scala.jfrog.io/artifactory/debian/sbt-1.2.7.deb \
&& dpkg -i sbt-1.2.7.deb \
&& rm sbt-1.2.7.deb \
&& sbt -Dsbt.rootdir=true sbtVersion"

# Install LLVM
RUN apt install lsb-release wget software-properties-common gnupg -y \
    && /bin/bash -c "$(wget -O - https://apt.llvm.org/llvm.sh)"

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8
ENV PATH /usr/lib/llvm-15/bin:$PATH
