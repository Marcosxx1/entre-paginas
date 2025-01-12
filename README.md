# Entre-Páginas

## Requisitos

- *Docker* instalado.
- *Java 17+*.
- Banco de dados e aplicação serão executados pelos contêineres Docker.

# EntrePaginas

Esta aplicação foi desenvolvida com a ajuda (essencial) de [LuizFNeto20](https://github.com/LuizFNeto20) e [Pablo](https://github.com/?), Luiz responsável pelo frontend. O backend foi arquitetado por ele, por mim e pelo Pablo, abrangendo tanto a arquitetura quanto a modelagem de dados do banco.

A implementação do backend ficou a cargo de Luiz e de mim, utilizando o *Thymeleaf* como motor de templates, Java 17.


Para rodar a aplicação

Verifique a versão do java: java --version deve ser maior que >

Docker deve estar rodando

Clone a aplicação
https://github.com/Marcosxx1/entre-paginas.git

mvn clean package -DskipTests

## Resumo

O projeto *EntrePaginas* é uma aplicação web projetada para facilitar a troca de livros entre usuários, promovendo o
consumo consciente e incentivando a socialização em comunidades virtuais. Através de um modelo incremental de
desenvolvimento, a plataforma atende aos requisitos idealizados e oferece uma interface simples e intuitiva, eliminando
barreiras de acesso e promovendo uma experiência fluida para os usuários.



---

## Problema Identificado

Durante o setup inicial do projeto, foram encontrados os seguintes problemas:

1. *Erro ao gerar o JAR:*
   - Ao executar mvn clean package, a aplicação quase completa o build, mas o arquivo JAR não é gerado corretamente.
   - Tentando executar docker-compose up --build, é exibido o erro:

     => ERROR [app 2/2] COPY target/entrepaginas-0.0.1-SNAPSHOT.jar app.jar  0.0s

   - O comando mvn clean package tenta conectar ao banco de dados, o que não deveria acontecer nessa etapa.

2. *Erro do Thymeleaf:*
   - O Thymeleaf não consegue localizar os templates dentro do contêiner Docker porque os recursos estão embutidos no
     JAR e não acessíveis diretamente no sistema de arquivos do contêiner.

---

## Solução

### Para Resolver o Erro do JAR

1. Compile o projeto sem executar os testes:
   bash
   mvn clean package -DskipTests

2. Construa e inicie os contêineres:
   bash
   docker-compose up --build

3. Verifique o conteúdo do JAR gerado (opcional):
   bash
   docker exec -it <container_id> /bin/sh
   jar tf target/entrepaginas-0.0.1-SNAPSHOT.jar | grep templates


### Para Resolver o Erro do Thymeleaf

1. *Configurar o Prefixo do Thymeleaf:*
   - No arquivo de configuração do Spring Boot application.yml, ajuste o prefixo para:
      - Se estiver usando application.yml:
        yaml
        spring:
        thymeleaf:
        prefix: classpath:/templates/


2. *Verificar a Estrutura do Projeto:*
   - Devemos nos certificar de que os templates estão no diretório src/main/resources/templates/.

3. *Recompilar e Reconstruir a Aplicação:*
   bash
   mvn clean package -DskipTests


4. *Recriar e Reiniciar os Contêineres Docker:*
   bash
   docker-compose build
   docker-compose up -d


5. *Ajustar o Caminho dos Fragmentos no Thymeleaf:*
   - Nos arquivos como Index.html, devemos verificar se as referências aos fragmentos estão corretas. Exemplo:
     html
     <!--Não vai funcionar-->            ↓ 
     <div th:replace="~{fragments/header/header :: header}"></div>

     <!--Vai funcionar-->                ↓ 
     <div th:replace="~{fragments/header/Header :: header}"></div>

   - Certifique-se de que o fragmento header está definido no arquivo Header.html:
     html
     <div th:fragment="header">
         <!-- Conteúdo do cabeçalho -->
     </div>


Isso ocorre porque:

### Configuração do Caminho no Thymeleaf:

Quando definimos

html
<!--Não vai funcionar-->            ↓
<div th:replace="~{fragments/header/header :: header}"></div>


o Thymeleaf tenta localizar o arquivo header.html dentro da pasta fragments/header/.
Se o arquivo estiver nomeado como Header.html (com "H" maiúsculo), ele não será encontrado no Linux/Docker, gerando um
erro.

### Resolução dos Fragmentos:

O nome do fragmento especificado no atributo th:fragment deve coincidir exatamente com o usado em th:replace.
No exemplo:

html                             
↓[1]      ↓[2]
<div th:replace="~{fragments/header/Header :: header}"></div>


Header[1] refere-se ao arquivo Header.html. `header[2] `refere-se ao nome do fragmento definido dentro do arquivo.
Boa Prática no Desenvolvimento:

Para evitar problemas relacionados a diferenças entre ambientes (local, Windows, Linux/Docker), recomenda-se seguir um
padrão consistente:
Use sempre a mesma convenção de nomenclatura (por exemplo, tudo em minúsculas ou PascalCase) para nomes de arquivos e
referências nos templates.
---