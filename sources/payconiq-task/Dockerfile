FROM openjdk:17-jdk-alpine3.14

RUN addgroup -S payconiq && adduser -S payconiq-admin -G payconiq
USER payconiq-admin:payconiq

ARG CLASSPATH_LIB=standalone-deploy/lib
ARG SOURCE_JAR=standalone-deploy/*.jar
ARG TARGET_JAR=payconiq-task.jar

WORKDIR /app

ENV PORT=8080
ENV EXEC_JAR=${TARGET_JAR}
ENV MAIN_CLASS=com.payconiq.geektastic.Main

COPY ${SOURCE_JAR} service-registry.jar
COPY ${CLASSPATH_LIB} ./lib

ENTRYPOINT java -cp ${EXEC_JAR}:./lib/* \
            -Xms64m \
            -Xmx128m \
            -XX:+UseZGC \
            -XX:+UseStringDeduplication \
            ${MAIN_CLASS}