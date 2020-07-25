FROM gradle:jdk14
COPY . /usr/local/src
WORKDIR /usr/local/src
RUN ["gradle", "build"]
ENTRYPOINT ["java", "-jar", "app-1.0.jar"]
