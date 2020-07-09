FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradle", "build"]
CMD ["java", "-Dserver.port=$PORT", "$JAVA_OPTS", "-jar", "build/libs/*.jar"]
