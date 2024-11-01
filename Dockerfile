FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
