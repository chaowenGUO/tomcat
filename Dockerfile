FROM gradle:jdk14
COPY build.gradle /usr/local/src/
COPY src /usr/local/src/src/
WORKDIR /usr/local/src/
RUN ["gradle", "build"]
ENTRYPOINT ["java", "-jar", "build/libs/app-1.0.jar"]
