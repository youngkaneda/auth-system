FROM openjdk:8-jdk-alpine

COPY ./target/provider-1.0.jar /opt/provider-1.0.jar
COPY ./lib /opt/lib

CMD java -cp "/opt/provider-1.0.jar:/opt/lib/*" provider.Loader
