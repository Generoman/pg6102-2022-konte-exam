FROM maven:3.6-jdk-11 as builder
WORKDIR /processing-service
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder processing-service/target/processing-service-1.0.jar /app/processing-service.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "/app/processing-service.jar"]