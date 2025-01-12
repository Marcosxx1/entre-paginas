# Usando a imagem base do OpenJDK
FROM openjdk:17-jdk-slim
VOLUME /tmp

# Instala o Bash
RUN apt-get update && apt-get install -y bash && apt-get clean && rm -rf /var/lib/apt/lists/*

# Copia o arquivo .jar da pasta target para o contêiner
COPY target/entrepaginas-0.0.1-SNAPSHOT.jar app.jar

# Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
