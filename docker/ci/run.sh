#!/bin/sh

docker run --rm \
    -it \
    --mount type=bind,src="$(pwd)/../../",dst="/root/csvman_test" \
    --name csvman_test csvman_test:1.1 \
    /bin/bash -c 'source "/root/.sdkman/bin/sdkman-init.sh" && cd /root/csvman_test && sbt test'
