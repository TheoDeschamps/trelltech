# Étape 1 : Image de base avec OpenJDK et Gradle
FROM gradle:8.2-jdk17 AS build

WORKDIR /app

COPY gradlew gradlew.bat settings.gradle.kts ./
COPY gradle gradle
COPY app /app
COPY app/.env /app/.env

RUN chmod +x gradlew
RUN ./gradlew :app:build --no-daemon
RUN find /app -name ".env"


# Étape 2 : Image finale pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/app-all.jar app.jar
COPY --from=build /app/.env .env
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
