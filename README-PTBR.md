# 🥘 API de Gerenciameto de Refeições Saudáveis (Projeto de TCC)

![Unlockway Logo](./.github/assets/unlockway_logo.png)

![Azure](https://img.shields.io/badge/azure-%230072C6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white) ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

Este projeto foi construído na faculdade como parte de um projeto de integração entre várias disciplinas (TCC). A API de Gerenciamento de Refeições Saudáveis é uma API robusta e escalável projetada para gerenciar rotinas de refeições saudáveis e notificações para os usuários. Construída usando Spring Boot com Maven, segue os princípios da Arquitetura MVC e Hexagonal. A API utiliza Hibernate JPA para interações com o banco de dados PostgreSQL, rodando localmente via Docker e Docker Compose. Para produção, a API é implantada usando Azure App Services, e as imagens são armazenadas usando Azure Blob Storage. A autenticação é feita usando tokens JWT, que expiram em uma hora. Os endpoints da API são documentados com Swagger. A API é usada para um aplicativo móvel Flutter. Você pode encontrar o repositório do aplicativo móvel [aqui](https://github.com/unlockway/unlockway_mobile).

> Acesse o protótipo de layout construído no Figma clicando [aqui](https://www.figma.com/design/eVCIm7PVUYjDSV6PrifbAG/Fatec---Projeto-Integrador%3A-Unlockway?node-id=0-1&t=JH2hcRXH9DhEu4BF-1)

## Diagrama de Classe

![Diagrama de Classe](./.github/assets/class_diagram.png)

## Diagrama de Sequência

- Criação de uma Refeição

![Diagrama de Sequência para criação de uma refeição](./.github/assets/sequence_diagram_create_meal.png)

- Criação de uma Rotina

![Diagrama de Sequência para criação de uma rotina](./.github/assets/sequence_diagram_create_routine.png)

## Casos de Uso

![Casos de Uso](./.github/assets/use_cases.png)

## Preview

![Swagger Page Preview](./.github/assets/unlockway_api_full_swagger.jpeg)

## Tecnologias

- **Java 17**
- **Spring Boot**
- **Maven**
- **Hibernate JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Azure App Services**
- **Azure Blob Storage**
- **JWT Authentication**
- **Swagger**
- **Flutter (Mobile Application)**

## Como configurar sua máquina

1. **Clone o repositório:**

   ```sh
   git clone https://github.com/unlockway/unlockway_api_v2.git
   cd ./unlockway_api_v2
   ```

2. **Instale o Docker e o Docker Compose:**

   - [Docker Installation Guide](https://docs.docker.com/get-docker/)
   - [Docker Compose Installation Guide](https://docs.docker.com/compose/install/)

3. **Configure o banco de dados Postgre:**

   ```sh
   docker-compose up --build -d # or `docker compose up --build -d (whitout the -)`
   ```

4. **Configure as propriedades da aplicação:**
   - Atualize o arquivo `application.properties` com as variáveis de produção necessárias caso queira rodar em produção.

## Como Executar a API

1. **Instale as dependências:**

   ```sh
   mvn clean install
   ```

2. **Rode a aplicação:**

   ```sh
   mvn spring-boot:run
   ```

3. **Acesse a documentação do Swagger:**
   - Navegue para `http://localhost:8080/swagger-ui/index.html` para visualizar e testar os endpoints.

Você também pode usar um editor de código (IDE) de sua preferência para executar a API. Aqui vai algumas opções populares:

- [Visual Studio Code](https://code.visualstudio.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Como contribuir

1. **Fork o repositório**

2. **Crie uma nova branch:**

   ```sh
   git checkout -b feature/your-feature-name
   ```

3. **Faça suas alterações e então crie um commit:**

   ```sh
   git commit -m "Add your message here"
   ```

4. **Empurre suas alterações pra uma branch remota:**

   ```sh
   git push origin feature/your-feature-name
   ```

5. **E por fim crie um Pull Request**

### Contribuidores

| ![Victor H. Silva](https://github.com/vickttor.png) | ![Bruno Pequeno](https://github.com/bruno-pequeno.png) | ![Daniel Vieira](https://github.com/DanielVieira2828.png) | ![Felipe Thaylan](https://github.com/ThaylanFe.png) | ![João Zavisas](https://github.com/Zavisas.png) |
| :-------------------------------------------------: | :----------------------------------------------------: | :-------------------------------------------------------: | :-------------------------------------------------: | :---------------------------------------------: |
|   [Victor H. Silva](https://github.com/vickttor)    |   [Bruno Pequeno](https://github.com/bruno-pequenor)   |   [Daniel Vieira](https://github.com/DanielVieira2828)    |   [Felipe Thaylan](https://github.com/ThaylanFe)    |   [João Zavisas](https://github.com/Zavisas)    |
