FROM openjdk:17-jdk-slim
LABEL authors="Professional"
WORKDIR /app
COPY target/TelegramTestsMore-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]