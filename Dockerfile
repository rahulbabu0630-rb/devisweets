# Use official OpenJDK 17 image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy SSL certificate (if needed)
COPY src/main/resources/certs/ca.pem /app/certs/ca.pem

# Copy the built JAR file
COPY target/attendance-*.jar app.jar

# Expose the port
EXPOSE ${PORT:-8080}

# Set timezone
ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Run with production profile and SSL support
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]