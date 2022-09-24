# docker

## ci
This is for ci.

First, move the directory to './docker/ci' .
And, build docker image.
```
$ docker build -t csvman_test:1.1 .
```

Please run the following command.
```
sh run.sh
```
The tests will be executed on the container and output test-report.txt to report dir.


## xxx-native-build
This is for native-build on different OSs.

First, build docker image.
```

```

Please run the following command.
```
build.sh
```
The scala-native will build an executable file that matches the OS and output the file to target dir.
