#########################################
# Build Stage: Build the application using Maven
#########################################
FROM maven:3.8.1-openjdk-17 as builder

WORKDIR /app

# Copy the pom.xml and build dependencies first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src src
RUN mvn package -DskipTests

#########################################
# Package Stage: Create the final container image
#########################################
FROM openjdk:17

# Set environment variables (allow for dynamic port configuration)
ENV SERVER_PORT=${SERVER_PORT:-8081}

# Copy the built JAR file from the build stage to the final image
COPY --from=builder /app/target/sbook-network-0.0.1-SNAPSHOT.jar /book-network-0.0.1-SNAPSHOT.jar

# Expose the port that the application will run on
EXPOSE ${SERVER_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "/book-network-0.0.1-SNAPSHOT.jar"]
