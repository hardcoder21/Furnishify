# Use the official OpenJDK base image
FROM maven:3.8-openjdk-11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and the project POM file
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

# Copy the project source
COPY src src

# Give execute permission to the Maven wrapper
RUN chmod +x mvnw

# Build the application
RUN mvn clean install -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "/app/target/Snekyfit_backend.jar"]
