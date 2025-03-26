FROM node:23 AS ng-build

WORKDIR /src

RUN npm i -g @angular/cli

COPY frontend/angular/public public
COPY frontend/angular/src src
COPY frontend/angular/*.json .

RUN npm ci && ng build

FROM eclipse-temurin:23-jdk AS j-build

WORKDIR /src

COPY backend/java/.mvn .mvn
COPY backend/java/src src
COPY backend/java/mvnw .
COPY backend/java/pom.xml .
COPY backend/java/src/main/resources/firebase.config.json src/main/resources/

# Copy angular files over to static
COPY --from=ng-build /src/dist/vttp_5a_final_project/browser/* src/main/resources/static

RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true

# Copy the JAR file over to the final container
FROM eclipse-temurin:23-jre 

WORKDIR /app

COPY --from=j-build /src/target/vttp_5a_final_project-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

SHELL [ "/bin/sh", "-c" ]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
