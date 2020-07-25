FROM gradle:jdk14
COPY build.gradle src /usr/local/src/
WORKDIR /usr/local/src/
RUN ["gradle", "build"]
ENTRYPOINT ["java", "-jar", "build/libs/app-1.0.jar"]
