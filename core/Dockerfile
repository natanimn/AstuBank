FROM maven AS build-core

WORKDIR /build

COPY pom.xml .
COPY common/pom.xml ./common/pom.xml
COPY desktop/pom.xml ./desktop/pom.xml
COPY bot/pom.xml ./bot/pom.xml
COPY core/pom.xml ./core/pom.xml

RUN mvn -N install

RUN mvn -pl core -am dependency:go-offline -B

COPY common/src ./common/src
COPY core/src ./core/src

RUN mvn -pl core -am clean package -DskipTests

FROM eclipse-temurin:21-alpine

WORKDIR /core

COPY --from=build-core /build/core/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]