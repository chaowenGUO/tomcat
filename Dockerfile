FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradlew", "build"]
CMD ["java", "-Dserver.port=$PORT", "-jar", "build/libs/*.jar"]
