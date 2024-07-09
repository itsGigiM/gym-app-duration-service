FROM openjdk:21-jdk

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ./target/gym-app-work-hours-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]