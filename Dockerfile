FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["ls", "-al", "/app"]
RUN ["gradle", "build"]
ENTRYPOINT ["java", "-jar", "build/libs/app-1.0.jar"]
