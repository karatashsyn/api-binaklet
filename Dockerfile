FROM openjdk:8
EXPOSE 8080
ADD target/binaklet.jar binaklet.jar
ENTRYPOINT ["java", "-jar", "binaklet.jar"]


