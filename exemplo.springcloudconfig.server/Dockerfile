FROM gradle:jdk8 AS build

WORKDIR /build/

ADD . .

RUN \
    gradle build && \
    mv build/libs/exemplo.springcloudconfig.server-0.0.1-SNAPSHOT.jar app.jar

# ---------------------------------------------

FROM openjdk:8-alpine as distribuicao

WORKDIR /data/

COPY --from=build /build/app.jar app.jar

ARG PROFILES
ARG PORT
ARG SPRING_PROFILES_ACTIVE
ARG JAVA_OPTS
ARG SERVER_PORT
ARG PATH_JAR

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE:-${PROFILES:-default}}
ENV JAVA_OPTS ${JAVA_OPTS:-'-Xmx1g'}
ENV SERVER_PORT ${SERVER_PORT:-${PORT:-9999}}

RUN apk add curl

EXPOSE ${SERVER_PORT}

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar app.jar