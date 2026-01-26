FROM gcr.io/distroless/java17-debian12

WORKDIR /app

COPY target/fruit-box-service.jar app.jar

EXPOSE 8080

USER nonroot

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+ExitOnOutOfMemoryError","-jar","app.jar"]
