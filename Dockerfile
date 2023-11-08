FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=./target/*.jar

COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]