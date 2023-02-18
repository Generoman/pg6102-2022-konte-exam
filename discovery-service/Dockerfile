FROM maven:3.6-jdk-11 as builder
WORKDIR /discovery-service
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder discovery-service/target/discovery-service-1.0.jar /app/discovery-service.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/discovery-service.jar"]