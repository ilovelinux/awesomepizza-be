FROM eclipse-temurin:21-alpine as BUILDER

LABEL org.opencontainers.image.authors="Antonio Spadaro"

COPY . .

RUN ./gradlew clean bootJar

FROM eclipse-temurin:21-alpine

LABEL org.opencontainers.image.authors="Antonio Spadaro"

COPY --from=BUILDER build/libs/be-0.0.1-SNAPSHOT.jar be-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","be-0.0.1-SNAPSHOT.jar"]