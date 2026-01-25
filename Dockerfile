# -------- BUILD --------
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q clean package -DskipTests

# -------- RUNTIME --------
FROM gcr.io/distroless/java17-debian12
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
USER nonroot
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-jar","app.jar"]
