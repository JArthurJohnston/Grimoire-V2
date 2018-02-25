#!/bin/bash

cd ~

if [ $EUID != 0 ]; then
	echo "Pleas run this script as root"
	exit 1
fi

JDK_LOC=$(find -type f -name "jdk*.tar.gz")
JRE_LOC=$(find -type f -name "jre*.tar.gz")

echo "Found JDK: $JDK_LOC"
echo "Found JRE: $JRE_LOC"

if [ -n "$JDK_LOC" ] && [ -n "$JRE_LOC" ]; then
	mkdir /opt/Java
	tar xvzf $JDK_LOC -C /opt/Java
	tar xvzf $JRE_LOC -C /opt/Java

	cd /opt/Java/jdk*
	#JDK_BIN_LOC=$(find -type d -name "bin")
	# ^This part still needs some work

	JDK_BIN_LOC="/opt/Java/jdk-9.0.4/bin"

	echo "export JAVA_HOME=$JDK_BIN_LOC" > /etc/profile.d/java_home.sh
	source /etc/profile.d/java_home.sh
fi

