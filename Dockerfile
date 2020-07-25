FROM gradle:jdk14
COPY build.gradle /app
COPY src /app/src
WORKDIR /app
RUN ["gradle", "build"]
ENTRYPOINT ["java", "-jar", "app-1.0.jar"]
