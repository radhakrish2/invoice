# Use a minimal JRE image
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app
# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose port (should match your Spring Boot server.port)
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
