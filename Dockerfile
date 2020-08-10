FROM gradle:jdk11 AS ship

RUN apt update \
	&& apt install vim -y

COPY . /opt/app/

WORKDIR /opt/app

RUN gradle build


FROM openjdk:11.0.8-jre

COPY --from=ship /opt/app/build/lib/*.jar /opt/app.jar

WORKDIR /opt/

CMD java -jar app.jar
