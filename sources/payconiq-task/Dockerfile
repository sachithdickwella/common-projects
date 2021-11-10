FROM openjdk:17-jdk-alpine3.14

ARG JAR_FILE=standalone-deploy/*.jar
ARG LIB_DIR=standalone-deploy/lib/
ARG CONF_DIR=standalone-deploy/configs/

WORKDIR /app

COPY ${JAR_FILE} ./payconiq-task.jar
COPY ${LIB_DIR} lib/.
COPY ${CONF_DIR} configs/.

ENV PORT=8080
ENV EXEC_JAR=payconiq-task.jar
ENV MAIN_CLASS=com.payconiq.geektastic.Main

RUN mkdir logs/

VOLUME ["/app/configs", "/app/logs"]

ENTRYPOINT java -cp ${EXEC_JAR} \
                -Xms64m \
                -Xmx512m \
                -XX:+UseZGC \
                -XX:+UseStringDeduplication \
                ${MAIN_CLASS}