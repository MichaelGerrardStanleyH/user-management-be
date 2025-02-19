FROM openjdk:17-jdk-alpine

COPY target/user-management-0.0.1-SNAPSHOT.jar /app/user-management-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/user-management-0.0.1-SNAPSHOT.jar"]