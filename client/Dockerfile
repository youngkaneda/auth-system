FROM openjdk:8-jdk-alpine

COPY ./target/client-1.0.jar /opt/client-1.0.jar
COPY ./lib /opt/lib

CMD java -cp "/opt/client-1.0.jar:/opt/lib/*" client.Test
