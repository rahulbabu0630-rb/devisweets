FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Create certs directory and copy certificate
RUN mkdir -p /app/certs
COPY src/main/resources/certs/ca.pem /app/certs/ca.pem

# Copy application JAR
COPY target/attendance-0.0.1-SNAPSHOT.jar app.jar

# Set non-sensitive defaults
ENV DB_PORT=10699
ENV FRONTEND_URL=http://localhost:5173
ENV SSL_CERT_PATH=/app/certs/ca.pem

# Sensitive variables should be passed at runtime
# (These are documented here but not set)
# ENV SPRING_DATASOURCE_URL=
# ENV SPRING_DATASOURCE_USERNAME=
# ENV SPRING_DATASOURCE_PASSWORD=

EXPOSE 10699
# Add this line before ENTRYPOINT
# Add this line before ENTRYPOINT
COPY .env .
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]