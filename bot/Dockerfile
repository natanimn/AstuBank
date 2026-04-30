FROM maven AS build-bot

WORKDIR /build

COPY pom.xml .
COPY common/pom.xml ./common/pom.xml
COPY desktop/pom.xml ./desktop/pom.xml
COPY bot/pom.xml ./bot/pom.xml
COPY core/pom.xml ./core/pom.xml

RUN mvn -N install

RUN mvn -pl bot -am dependency:go-offline -B

COPY common/src ./common/src
COPY bot/src ./bot/src

RUN mvn -pl bot -am clean package -DskipTests

FROM eclipse-temurin:21-alpine

WORKDIR /bot

COPY --from=build-bot /build/bot/target/bot-*.jar app.jar

CMD ["java", "-jar", "app.jar"]



