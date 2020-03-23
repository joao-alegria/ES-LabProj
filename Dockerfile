FROM openjdk:8-jdk
RUN apt-get update -y && apt-get install -y maven
COPY iss/ /iss
WORKDIR /iss
RUN mvn compile
ENTRYPOINT ["java","-jar","/iss/target/iss-0.0.1-SNAPSHOT.jar"]