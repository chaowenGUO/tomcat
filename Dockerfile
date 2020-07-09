FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradle", "build"]
CMD ["java", "-jar", "build/libs/app-1.0.jar"]
