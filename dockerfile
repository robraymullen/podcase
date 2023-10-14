FROM maven as builder

WORKDIR /usr/podcase
COPY ./src ./src
COPY ./static ./static
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml
COPY ./application-prod.properties ./application.properties

RUN mvn package spring-boot:repackage

FROM openjdk:13 

WORKDIR /usr/local/bin

COPY --from=builder /usr/podcase/target /usr/local/bin
COPY --from=builder /usr/podcase/application.properties /usr/local/bin/application.properties
ENTRYPOINT ["java","-jar","./podcase-0.0.1.jar"]