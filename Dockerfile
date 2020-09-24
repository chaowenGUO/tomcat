FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradle", "build"]
RUN ["unzip", "app-1.0.jar"]
ENTRYPOINT ["java", "-jar", "app-1.0.jar"]
