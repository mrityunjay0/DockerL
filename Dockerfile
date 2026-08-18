FROM openjdk

WORKDIR /app

COPY target/DockerL-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080