FROM openjdk:17-slim

WORKDIR /app

COPY target/IoTServer-1.0-SNAPSHOT.jar /app/IoTServer-1.0-SNAPSHOT.jar

EXPOSE 9090

CMD ["java", "-jar", "IoTServer-1.0-SNAPSHOT.jar"]