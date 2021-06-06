FROM openjdk:11-jdk
MAINTAINER chanyeol@edu.hanbat.ac.kr

WORKDIR /
ADD ./build/libs/virtual-cnc-*.jar app.jar

EXPOSE 9013

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]