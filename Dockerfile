FROM gradle:jdk15
COPY *.html *.js *.sql Server.java build.gradle /usr/local/src/
WORKDIR /usr/local/src/
RUN ["gradle", "copyDependencies"]
RUN ["javac", "-cp", "copyDependencies/*:.", "Server.java"]
ENTRYPOINT ["java", "-cp", "copyDependencies/*:.", "Server"]
