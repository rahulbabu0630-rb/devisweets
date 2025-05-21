FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
RUN mkdir -p /app/certs
COPY src/main/resources/certs/ca.pem /app/certs/ca.pem

COPY target/attendance-0.0.1-SNAPSHOT.jar app.jar

ENV DB_PORT=10699
ENV FRONTEND_URL=http://localhost:5173
ENV SSL_CERT_PATH=/app/certs/ca.pem
EXPOSE 10699
COPY .env .
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
