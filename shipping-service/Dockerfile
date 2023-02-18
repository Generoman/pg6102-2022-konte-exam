FROM maven:3.6-jdk-11 as builder
WORKDIR /shipping-service
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder shipping-service/target/shipping-service-1.0.jar /app/shipping-service.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app/shipping-service.jar"]