# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos solo lo necesario, excluyendo .env explícitamente (opcional)
COPY . .
# O puedes usar:
# COPY pom.xml mvnw mvnw.cmd ./
# COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV PORT=9090
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]