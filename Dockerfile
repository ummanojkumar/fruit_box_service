FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/fruit-box-service.jar fruit-box-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/fruit-box-service.jar"]
