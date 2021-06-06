FROM openjdk:11-jdk
MAINTAINER chanyeol@edu.hanbat.ac.kr

WORKDIR /
ADD ./build/libs/sensing-*.jar app.jar

EXPOSE 9012

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]