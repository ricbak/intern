FROM adoptopenjdk/openjdk11:alpine
COPY ./target/recognition-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "recognition-0.0.1-SNAPSHOT.jar"]