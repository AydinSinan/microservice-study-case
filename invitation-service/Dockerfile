FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/invitation-service.jar invitation-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "invitation-service.jar"]