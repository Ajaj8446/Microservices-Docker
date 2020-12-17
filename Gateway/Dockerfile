FROM openjdk:8
ADD target/gateway-service.jar gateway-service.jar 
EXPOSE 8762
ENTRYPOINT ["java", "-jar","gateway-service.jar"]