FROM adoptopenjdk/openjdk11 as build

WORKDIR /build

COPY pom.xml .
COPY mvnw .
COPY .mvn/ ./.mvn
RUN sh mvnw dependency:go-offline

COPY src/ ./src
RUN sh mvnw -DskipTests=true package

FROM openjdk:11-jre-slim
COPY --from=build /build/target/*.jar bot-app.jar
ENTRYPOINT ["java","-jar","/bot-app.jar"]