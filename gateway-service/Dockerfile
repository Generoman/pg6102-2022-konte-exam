FROM maven:3.6-jdk-11 as builder
WORKDIR /gateway-service
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder gateway-service/target/gateway-service-1.0.jar /app/gateway-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/gateway-service.jar"]