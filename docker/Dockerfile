FROM openjdk:10.0.2-jre

EXPOSE 8080

WORKDIR /opt/farm-control

COPY ./target/farm-control.jar ./farm-control.jar

CMD ["java", "-jar", "-Xms64m", "-Xmx64m", "farm-control.jar"]
