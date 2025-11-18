# ---------------------
# Stage 1 — BUILD
# ---------------------
FROM eclipse-temurin:25.0.1_8-jdk-ubi10-minimal AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests


# ---------------------
# Stage 2 — RUN
# ---------------------
FROM eclipse-temurin:25-ubi10-minimal

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
