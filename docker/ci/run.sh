#!/bin/sh

docker run --rm -it --mount type=bind,src="$(pwd)/../../",dst="/root/csvman_test" --name csvman_test csvman_test:1.0 /bin/bash -c "sbt test" "exit"
