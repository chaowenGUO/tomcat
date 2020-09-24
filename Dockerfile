FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradle", "build"]
RUN ["unzip", "app-1.0.jar"]
RUN ["ls"]
ENTRYPOINT ["java", "-jar", "app-1.0.jar"]
