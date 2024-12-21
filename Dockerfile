# Use an official JDK runtime as a parent image
FROM openjdk:21-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the container
COPY target/fsdm_it_club-0.0.1-SNAPSHOT.jar /app/fsdmitclub-platform.jar

# Make port 8080 available to the world outside this container
EXPOSE 80

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/fsdmitclub-platform.jar"]