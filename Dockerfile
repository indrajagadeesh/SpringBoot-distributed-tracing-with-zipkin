FROM gradle:jdk11 AS jar-build

RUN apt update \
	&& apt install vim -y

COPY . /opt/app/

WORKDIR /opt/app

RUN gradle build


FROM openjdk:11.0.8-jre AS main-image

COPY --from=jar-build /opt/app/build/libs/TracingModule*.jar /opt/app.jar

WORKDIR /opt/

CMD java -jar app.jar