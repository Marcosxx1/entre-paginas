# Entre-Páginas
<div align="center">
  <img src="https://github.com/user-attachments/assets/0293fc5d-dda4-4d2a-9650-23004236a890" alt="logo" />
  <p><i>Idealização da logo: <a href="https://github.com/LuizFNeto20">LuizFNeto20</a></i></p>
</div>

## Requisitos

- **Docker** instalado.
- **Java 20**.
- Banco de dados e aplicação serão executados pelos contêineres Docker.

## Sobre o Projeto

Esta aplicação foi desenvolvida com a ajuda essencial de [LuizFNeto20](https://github.com/LuizFNeto20)
e [Pablo](https://github.com/?). O Luiz foi responsável pelo frontend, enquanto o backend foi arquitetado por ele, por
mim e pelo Pablo, abrangendo tanto a arquitetura quanto a modelagem de dados do banco.

A implementação do backend ficou a cargo de Luiz e de mim, utilizando o **Thymeleaf** como motor de templates.

## Como Executar a Aplicação

1. **Verifique os requisitos:**
    - Certifique-se de que a versão do Java instalada é a **20**.
    - O Docker deve estar em execução.

2. **Clone o repositório:**
   ```bash
   git clone https://github.com/Marcosxx1/entre-paginas.git
   ```

3. **Troque para a branch `using_docker`:**
   ```bash
   git checkout using_docker
   ```

4. **Compile o projeto:**
   ```bash
   mvn clean package -DskipTests
   ```

5. **Suba os contêineres Docker:**
   ```bash
   docker-compose up --build
   ```

## Resumo

O projeto *EntrePaginas* é uma aplicação web projetada para facilitar a troca de livros entre usuários, promovendo o
consumo consciente e incentivando a socialização em comunidades virtuais. Através de um modelo incremental de
desenvolvimento, a plataforma atende aos requisitos idealizados e oferece uma interface simples e intuitiva, eliminando
barreiras de acesso e promovendo uma experiência fluida para os usuários.

---

## O que aprendemos e foi desenvolvido nesta aplicação

Este projeto foi uma oportunidade de consolidar conhecimentos e aplicar boas práticas no desenvolvimento de software.
Durante o desenvolvimento desta aplicação, exploramos e aprendemos sobre os seguintes conceitos e tecnologias:

1. **Arquitetura de Software**  
   Estruturamos a aplicação com uma arquitetura modular, organizando as responsabilidades em camadas claras: *
   *Controller**, **Service** e **Repository**. Essa separação promoveu manutenibilidade, escalabilidade e facilidade de
   entendimento do sistema.

2. **Modelagem de Dados**  
   Projetamos um modelo de dados robusto para atender às regras de negócio, utilizando conceitos de normalização e
   garantindo a integridade dos dados.

3. **UML (Unified Modeling Language)**  
   Utilizamos diagramas UML para representar visualmente os principais componentes do sistema, como casos de uso,
   diagramas de classes e de sequência, ajudando na comunicação entre a equipe e no planejamento das implementações.

4. **DER (Diagrama Entidade-Relacionamento)**  
   Criamos o Diagrama Entidade-Relacionamento (DER) para ilustrar as entidades do banco de dados, seus atributos e
   relacionamentos. Essa prática foi essencial para o alinhamento entre os requisitos do sistema e a modelagem do banco
   de dados.

5. **Programação Imperativa e Declarativa**  
   O projeto equilibra abordagens imperativas (controle explícito dos fluxos de execução) e declarativas (focando no "o
   que fazer" em vez de "como fazer"), com o uso de interfaces funcionais e implementações enxutas.

6. **Autenticação e Autorização com Spring Security**  
   Implementamos mecanismos de segurança, como autenticação e autorização, utilizando o `Authentication` do Spring
   Security para proteger rotas e garantir o acesso seguro aos recursos.

7. **Containerização com Docker**  
   Utilizamos containers para rodar a aplicação e o banco de dados PostgreSQL. Isso garantiu consistência entre
   ambientes de desenvolvimento e produção, além de facilitar a configuração do ambiente.

8. **Inversão de Controle e Injeção de Dependências**  
   Adotamos práticas de IoC e DI utilizando o Spring Framework, delegando ao framework o gerenciamento das dependências
   e promovendo o desacoplamento entre os componentes.

9. **Camadas de Serviço e Controle**  
   Adotamos controllers para gerenciar os endpoints e serviços para encapsular as regras de negócio, seguindo os
   princípios da responsabilidade única e organizando o código de forma modular.

10. **Log e Monitoramento**  
    Implementamos logs estratégicos nos controllers e serviços para rastrear acessos e facilitar o monitoramento do
    sistema, ajudando na depuração e manutenção.

11. **Banco de Dados Relacional (PostgreSQL)**  
    Desenvolvemos a aplicação com persistência em um banco de dados relacional, criando esquemas eficientes para
    entidades e relacionamentos.

12. **Colaboração e Controle de Versão**  
    Utilizamos o Git e repositórios compartilhados para gerenciar versões e colaborar de forma eficiente entre os
    membros da equipe, documentando o progresso e garantindo rastreabilidade.

---

## Próximos Passos

[LuizFNeto20](https://github.com/LuizFNeto20) e eu discutimos que, como nosso forte é o desenvolvimento backend, nosso
próximo desafio será a criação de uma aplicação REST baseada em **microserviços**, tomando como base a estrutura e as
funcionalidades desta aplicação.

Alguns objetivos definidos para este próximo projeto incluem:

1. **Foco em Microserviços**  
   Evoluiremos a arquitetura monolítica desta aplicação para um sistema distribuído baseado em microserviços, separando
   os domínios em serviços independentes, cada um com sua própria responsabilidade e banco de dados.

2. **Teste-Driven Development (TDD)**  
   Implementaremos testes desde o início, utilizando TDD como metodologia para desenvolver funcionalidades e garantir a
   qualidade do código.

3. **Adoção de Novas Técnicas e Ferramentas**  
   Exploraremos novas práticas que estamos aprendendo, como mensageria (ex.: RabbitMQ ou Kafka), circuit breakers (ex.:
   Resilience4j) e observabilidade (ex.: Prometheus e Grafana), além de reforçar conhecimentos em CI/CD para deploy
   automatizado.

4. **Redução do Escopo**  
   Nesta nova versão, deixaremos de lado o front-end e focaremos exclusivamente no backend, garantindo APIs robustas,
   documentadas e bem testadas.

Nosso objetivo é consolidar os aprendizados anteriores e avançar para um sistema mais escalável, resiliente e moderno,
aplicando boas práticas de desenvolvimento e arquitetura de software.


---

## Problemas Identificados

Pequena sessão para termos recursos de caso estes erros venham a acontecer novamente durante a containerização de novos
projetos

Durante o setup inicial do projeto, foram encontrados os seguintes problemas:

1. *Erro ao gerar o JAR:*
    - Ao executar mvn clean package, a aplicação quase completa o build, mas o arquivo JAR não é gerado corretamente.
    - Tentando executar docker-compose up --build, é exibido o erro:
    ```powershell
      => ERROR [app 2/2] COPY target/entrepaginas-0.0.1-SNAPSHOT.jar app.jar  0.0s
     ```
    - O comando mvn clean package tenta conectar ao banco de dados, o que não deveria acontecer nessa etapa.

2. *Erro do Thymeleaf:*
    - O Thymeleaf não consegue localizar os templates dentro do contêiner Docker porque os recursos estão embutidos no
      JAR e não acessíveis diretamente no sistema de arquivos do contêiner.

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

1. *Configurar o Prefixo do Thymeleaf:*
    - No arquivo de configuração do Spring Boot application.yml, ajuste o prefixo para:
        - Se estiver usando application.yml:
          ```yaml
          spring:
          thymeleaf:
          prefix: classpath:/templates/
            ```

2. *Verificar a Estrutura do Projeto:*
    - Devemos nos certificar de que os templates estão no diretório src/main/resources/templates/.

3. *Recompilar e Reconstruir a Aplicação:*
   ```bash
   mvn clean package -DskipTests
    ```

4. *Recriar e Reiniciar os Contêineres Docker:*
   ```bash
   docker-compose build
   docker-compose up -d
    ```

5. *Ajustar o Caminho dos Fragmentos no Thymeleaf:*
    - Nos arquivos como Index.html, devemos verificar se as referências aos fragmentos estão corretas. Exemplo:
      ```html 
      <!--Não vai funcionar-->            ↓ 
      <div th:replace="~{fragments/header/header :: header}"></div>

      <!--Vai funcionar-->                ↓ 
      <div th:replace="~{fragments/header/Header :: header}"></div>
        ```
    - Certifique-se de que o fragmento header está definido no arquivo Header.html:
      ```html 
      <div th:fragment="header">
          <!-- Conteúdo do cabeçalho -->
      </div>
        ```

Isso ocorre porque:

### Configuração do Caminho no Thymeleaf:

```bash
2025-01-12T23:52:59.598Z ERROR 1 --- [nio-9000-exec-5] o.a.c.c.C.[.[.[/].[dispatcherServlet] : Servlet.service() for servlet
[dispatcherServlet] in context with path [] threw exception [Request processing failed: org.thymeleaf.exceptions.TemplateInputException:
Error resolving template [fragments/header/header], template might not exist or might not be accessible by any of the
configured Template Resolvers (template: "Index" - line 33, col 10)] with root cause
```

Quando definimos

```html   
<!--Não vai funcionar-->            ↓
<div th:replace="~{fragments/header/header :: header}"></div>
```

o Thymeleaf tenta localizar o arquivo header.html dentro da pasta fragments/header/.
Se o arquivo estiver nomeado como Header.html (com "H" maiúsculo), ele não será encontrado no Linux/Docker, gerando um
erro.

### Resolução dos Fragmentos:

O nome do fragmento especificado no atributo th:fragment deve coincidir exatamente com o usado em th:replace.
No exemplo:

```html                            
                                    ↓[1]      ↓[2]
<div th:replace="~{fragments/header/Header :: header}"></div>
``` 

Header[1] refere-se ao arquivo Header.html. `header[2] `refere-se ao nome do fragmento definido dentro do arquivo.
Boa Prática no Desenvolvimento:

Para evitar problemas relacionados a diferenças entre ambientes (local, Windows, Linux/Docker), recomenda-se seguir um
padrão consistente:
Use sempre a mesma convenção de nomenclatura (por exemplo, tudo em minúsculas ou PascalCase) para nomes de arquivos e
referências nos templates.
---
