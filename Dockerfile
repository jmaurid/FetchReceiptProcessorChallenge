FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/FetchReceiptProcessorChallenge-1.0.0.jar fetchchallenge.jar
ENTRYPOINT ["java", "-jar", "/fetchchallenge.jar"]