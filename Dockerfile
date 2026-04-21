# syntax=docker/dockerfile:1.7

# ------------------------------------------------------------------
# Stage 1 - build: compila o JAR usando Maven + Temurin 21
# ------------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copia os arquivos de definicao primeiro para aproveitar o cache do Docker
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./
RUN chmod +x mvnw && ./mvnw -B -q -DskipTests dependency:go-offline

# Agora copia o codigo-fonte e gera o artefato
COPY src ./src
RUN ./mvnw -B -q -DskipTests package \
 && mv target/esg-restful.jar /build/app.jar

# ------------------------------------------------------------------
# Stage 2 - runtime: imagem minima com JRE 21 e usuario nao-root
# ------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS runtime

RUN apk add --no-cache curl \
 && addgroup -S spring \
 && adduser  -S spring -G spring

WORKDIR /app
COPY --from=build /build/app.jar /app/app.jar
RUN chown -R spring:spring /app
USER spring

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker \
    JAVA_OPTS="-XX:MaxRAMPercentage=75"

HEALTHCHECK --interval=20s --timeout=5s --start-period=60s --retries=5 \
  CMD curl -fsS http://localhost:${APP_PORT:-8080}/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
