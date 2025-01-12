# Entre-Páginas

## Requisitos
- **Docker** instalado.
- **Java 17+**.
- Banco de dados e aplicação serão executados pelos contêineres Docker.

---

## Problema Identificado
Durante o setup inicial do projeto, foram encontrados os seguintes problemas:

1. **Erro ao gerar o JAR:**
   - Ao executar `mvn clean package`, a aplicação quase completa o build, mas o arquivo JAR não é gerado corretamente.
   - Tentando executar `docker-compose up --build`, é exibido o erro:
     ```
     => ERROR [app 2/2] COPY target/entrepaginas-0.0.1-SNAPSHOT.jar app.jar  0.0s
     ```
   - O comando `mvn clean package` tenta conectar ao banco de dados, o que não deveria acontecer nessa etapa.

2. **Erro do Thymeleaf:**
   - O Thymeleaf não consegue localizar os templates dentro do contêiner Docker porque os recursos estão embutidos no JAR e não acessíveis diretamente no sistema de arquivos do contêiner.

---

## Solução

### Para Resolver o Erro do JAR
1. Compile o projeto sem executar os testes:
   ```bash
   mvn clean package -DskipTests
   ```
2. Construa e inicie os contêineres:
   ```bash
   docker-compose up --build
   ```
3. Verifique o conteúdo do JAR gerado (opcional):
   ```bash
   docker exec -it <container_id> /bin/sh
   jar tf target/entrepaginas-0.0.1-SNAPSHOT.jar | grep templates
   ```

### Para Resolver o Erro do Thymeleaf
1. **Configurar o Prefixo do Thymeleaf:**
   - No arquivo de configuração do Spring Boot (`application.properties` ou `application.yml`), ajuste o prefixo para:
      - Se estiver usando `application.properties`:
        ```properties
        spring.thymeleaf.prefix=classpath:/templates/
        ```
      - Se estiver usando `application.yml`:
        ```yaml
        spring:
          thymeleaf:
            prefix: classpath:/templates/
        ```

2. **Verificar a Estrutura do Projeto:**
   - Certifique-se de que os templates estão no diretório `src/main/resources/templates/`.

3. **Recompilar e Reconstruir a Aplicação:**
   ```bash
   mvn clean package -DskipTests
   ```

4. **Recriar e Reiniciar os Contêineres Docker:**
   ```bash
   docker-compose build
   docker-compose up -d
   ```

5. **Ajustar o Caminho dos Fragmentos no Thymeleaf:**
   - No arquivo `Index.html`, verifique se as referências aos fragmentos estão corretas. Exemplo:
     ```html
     <div th:replace="~{fragments/header/Header :: header}"></div>
     ```
   - Certifique-se de que o fragmento `header` está definido no arquivo `Header.html`:
     ```html
     <div th:fragment="header">
         <!-- Conteúdo do cabeçalho -->
     </div>
     ```

---