FROM gradle:jdk14
COPY . /app
WORKDIR /app
RUN ["gradle", "build"]
RUN ["unzip", "app-1.0.jar"]
WORKDIR /app/BOOT-INF/classes
ENTRYPOINT ["java", "-cp", "/app/BOOT-INF/lib/*:.", "Server"]
