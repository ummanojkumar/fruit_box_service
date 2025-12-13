FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/fruit-box-service.jar fruit-box-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "fruit-box-service.jar"]

#docker build -t fruit-box-backend:v2 .  --> create image
#docker run -p 8082:8959 vendor-management.jar
#docker run -d --name fruitbox-container -p 8080:8080 fruit-box-backend:v1   --> run an container
# docker logs fruitbox-container  --> check logs

