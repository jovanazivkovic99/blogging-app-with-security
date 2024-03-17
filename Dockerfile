#FROM eclipse-mosquitto
#EXPOSE 8081
#ADD target/blog.jar blog.jar
#ENTRYPOINT ["java","-jar","/blog.jar"]
FROM maven:3.9.6-eclipse-temurin-17 AS builder
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:17

COPY --from=builder /app/target/blog.jar blog.jar
ENTRYPOINT ["java","-jar","/blog.jar"]