FROM gradle:jdk14
COPY java static build.gradle /usr/local/src/
WORKDIR /usr/local/src/
RUN ["gradle", "copyDependencies"]
RUN ["ls", "lib"]
#WORKDIR /usr/local/src/BOOT-INF/classes
#ENTRYPOINT ["java", "-cp", "/usr/local/src/BOOT-INF/lib/*:.", "Server"]
