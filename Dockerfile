FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/venus-*.jar ./venus.jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","venus.jar"]
#CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dockermac","-jar","venus.jar"]
