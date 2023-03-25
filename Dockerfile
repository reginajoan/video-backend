FROM openjdk:11

COPY target/video-streaming-0.0.1-SNAPSHOT.jar videobackend.jar
EXPOSE 1010
ENTRYPOINT ["java", "-jar", "/videobackend.jar"]