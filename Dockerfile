FROM gradle:jdk14
COPY Server.java static build.gradle /usr/local/src/
WORKDIR /usr/local/src/
RUN ["gradle", "copyDependencies"]
RUN ["javac", "-cp", "copyDependencies/*:.", "Server.java"]
ENTRYPOINT ["java", "-cp", "copyDependencies/*:.", "Server"]
