# Build + test, then a small non-root runtime image
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /src
COPY src ./src
COPY test ./test
RUN mkdir -p out && javac -d out src/app/Main.java test/app/MainTest.java \
 && java -cp out app.MainTest \
 && rm out/app/MainTest.class

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app -g 10001 && adduser -S app -u 10001 -G app
WORKDIR /app
COPY --from=build /src/out ./classes
USER 10001
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-cp", "/app/classes", "app.Main"]
