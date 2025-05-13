FROM openjdk:21-jdk-slin

COPY ./out/artifacts/ServerSicilia_jar/ServerSicilia.jar /app/ServerSicilia.jar
COPY ./file.csv /app/file.csv

WORKDIR /app

EXPOSE 1134

CMD ["java","-jar","ServerSicilia.jar"]