FROM eclipse-temurin:21

ENV APP_HOME /app
ENV APP_JAR tinkoff-translate-service-0.0.1-SNAPSHOT.jar

RUN mkdir $APP_HOME

COPY build/libs/$APP_JAR $APP_HOME/translate-service.jar

WORKDIR $APP_HOME

ENTRYPOINT ["java", "-jar", "translate-service.jar"]