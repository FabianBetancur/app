FROM openjdk:17-alpine
WORKDIR /app-sprin-render
COPY build/libs/app-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
EXPOSE 9000
