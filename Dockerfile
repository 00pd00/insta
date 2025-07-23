# Use a lightweight OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything and build the app
COPY . .

# Build the app using Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Expose port (match your Spring Boot port)
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/insta-0.0.1-SNAPSHOT.jar"]
