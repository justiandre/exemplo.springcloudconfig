FROM gradle:jdk11 AS build

WORKDIR /build/

ADD . .

RUN \
    gradle build && \
    mv build/libs/exemplo.springcloudconfig.service02-0.0.1-SNAPSHOT.jar app.jar

# ---------------------------------------------

FROM adoptopenjdk/openjdk11:jdk-11.0.3_7-alpine as distribuicao

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
ENV SERVER_PORT ${SERVER_PORT:-${PORT:-8081}}

EXPOSE ${SERVER_PORT}

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar app.jar