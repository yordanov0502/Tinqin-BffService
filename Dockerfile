FROM amazoncorretto:21-alpine
LABEL authors="yordanov0502"
WORKDIR /app
EXPOSE 8083
COPY rest/target/rest-0.0.1-SNAPSHOT.jar /app/bff.jar

ENTRYPOINT ["java", "-jar", "/app/bff.jar"]