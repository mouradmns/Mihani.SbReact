FROM openjdk:19
ADD target/Myhanie-0.0.1-SNAPSHOT.jar Myhanie-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/Myhanie-0.0.1-SNAPSHOT.jar"]
