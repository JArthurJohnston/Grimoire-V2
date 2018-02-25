#!/bin/bash

cd ~

if [ $EUID != 0 ]; then
	echo "Pleas run this script as root"
	exit 1
fi

JDK_LOC=$(find -type f -name "jdk*.tar.gz")

echo "Found JDK: $JDK_LOC"

if [ -z "$JDK_LOC" ]; then
	echo "download the JDK tar.gz file"
	exit 2
fi

mkdir /opt/Java
tar xvzf $JDK_LOC -C /opt/Java

cd /opt/Java/jdk*
#JDK_BIN_LOC=$(find -type d -name "bin")
# ^This part still needs some work

JDK_OPT_LOC="/opt/Java/jdk-9.0.4"

echo "export JAVA_HOME=$JDK_OPT_LOC" > /etc/profile.d/java_home.sh
source /etc/profile.d/java_home.sh

echo "Done. Please restart your machine"

exit 0
