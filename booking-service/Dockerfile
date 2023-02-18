FROM maven:3.6-jdk-11 as builder
WORKDIR /booking-service
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder booking-service/target/booking-service-1.0.jar /app/booking-service.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app/booking-service.jar"]