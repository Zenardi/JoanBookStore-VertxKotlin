FROM openjdk:8-jre-alpine
MAINTAINER Books-Api
COPY target/bookstore-vertx-kotlin-1.0-SNAPSHOT-jar-with-dependencies.jar /home/books-api.jar
EXPOSE 8888
CMD ["java","-jar","/home/books-api.jar"]