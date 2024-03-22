FROM amazoncorretto:21-alpine-jdk

COPY build/libs/app-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]

EXPOSE 9000
