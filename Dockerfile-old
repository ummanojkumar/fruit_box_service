# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy only pom first (better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the exact JAR name
COPY --from=build /build/target/fruit-box-service.jar fruit-box-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "fruit-box-service.jar"]


#docker build -t fruit-box-backend:multi .
#docker run -d --name fruitbox-container -p 8080:8080 fruit-box-backend:multi
#docker logs fruitbox-container