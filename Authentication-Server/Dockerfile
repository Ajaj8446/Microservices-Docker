FROM openjdk:8
ADD target/authentication-service.jar authentication-service.jar 
EXPOSE 9100
ENTRYPOINT ["java", "-jar","authentication-service.jar"]