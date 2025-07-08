# Usa una imagen oficial de Java
FROM openjdk:17

# Crea el directorio de la app
WORKDIR /app

# Copia el archivo jar
COPY target/*.jar app.jar

# Expone el puerto del backend
EXPOSE 9090

# Comando para ejecutar el jar
ENTRYPOINT ["java", "-jar", "app.jar"]
