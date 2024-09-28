# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory of your project to the container
COPY target/IoTServer-1.0-SNAPSHOT.jar /app/IoTServer-1.0-SNAPSHOT.jar

# Expose the port your application will run on
EXPOSE 9090

# Define the command to run your application
CMD ["java", "-jar", "IoTServer-1.0-SNAPSHOT.jar"]