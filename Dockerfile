FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /
ARG VERSION
ADD /target/questions-$VERSION.jar app.jar
EXPOSE 8080
CMD java -jar app.jar