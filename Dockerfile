FROM openjdk:21-slim
LABEL authors="Hamza W. Amentag"

MAINTAINER eofe
COPY target/account-microservice-0.0.1-SNAPSHOT.jar account-microservice-0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar", "account-microservice-0.0.1-SNAPSHOT.jar"]