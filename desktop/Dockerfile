FROM maven AS build-desktop

WORKDIR /build

COPY pom.xml .
COPY common/pom.xml ./common/pom.xml
COPY desktop/pom.xml ./desktop/pom.xml
COPY bot/pom.xml ./bot/pom.xml
COPY core/pom.xml ./core/pom.xml

RUN mvn -N install
RUN mvn -pl desktop -am dependency:go-offline -B

COPY common/src ./common/src
COPY desktop/src ./desktop/src

RUN mvn -pl desktop -am clean package -DskipTests

FROM eclipse-temurin:21-jammy

WORKDIR /desktop

COPY --from=build-desktop /build/desktop/target/desktop-*.jar app.jar

CMD ["java", "-jar", "app.jar"]