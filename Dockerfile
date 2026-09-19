FROM maven:3.9-eclipse-temurin-19 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:19-jre-jammy
WORKDIR /app
RUN useradd -m -u 1001 appuser

COPY --from=builder /build/target/SpringApiTemplate-*.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher \
  -Dspring.profiles.active=prod -Dspring.boot.actuate.endpoints.web.exposure.include=health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
