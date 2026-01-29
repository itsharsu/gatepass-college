# Java 21 image
FROM eclipse-temurin:21-jdk-jammy

# Working directory inside container
WORKDIR /app

# Copy jar
COPY build/libs/*.jar app.jar

# Create upload folder
RUN mkdir -p /app/uploads/leaves

# Open port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
