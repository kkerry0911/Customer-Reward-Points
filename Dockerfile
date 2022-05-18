FROM gradle:7.4.2-jdk17 as build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build --no-daemon 

FROM openjdk:17.0.2-oracle

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

CMD ["java","-jar","/app/spring-boot-application.jar"]


