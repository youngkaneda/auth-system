FROM openjdk:8-jdk-alpine

ENV JUAN_REDIS_DT_PORT 6379
ENV JUAN_REDIS_DT_HOST 172.19.0.2 

COPY ./target/auth-system-1.0.jar /opt/auth-system-1.0.jar
COPY ./lib /opt/lib

CMD java -cp "/opt/auth-system-1.0.jar:/opt/lib/*" auth.system.Loader
