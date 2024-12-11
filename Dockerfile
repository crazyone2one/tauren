FROM alpine/java:21-jdk
MAINTAINER jingll <the2ndindec@gmail.com>
WORKDIR /app
VOLUME /app/files
ADD backend-1.0.0.jar tauren.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","tauren.jar"]