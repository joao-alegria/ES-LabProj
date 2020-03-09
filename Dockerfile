ARG kafka
FROM openjdk:8-jdk-alpine
ADD iss/target/iss-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--kafka=kafka"]