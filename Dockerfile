FROM openjdk:11
VOLUME /tmp
EXPOSE 8085
ADD ./target/PaymentService-0.0.1-SNAPSHOT.jar ms-payment.jar
ENTRYPOINT ["java","-jar","/ms-payment.jar"]