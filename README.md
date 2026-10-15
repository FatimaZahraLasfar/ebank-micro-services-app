# ebank-micro-services-app

[![Java](https://img.shields.io/badge/Java-21%20%2F%2027-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1%20%2F%204.1.1-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024%20%2F%202025-6DB33F)](https://spring.io/projects/spring-cloud)
[![Angular](https://img.shields.io/badge/Angular-21.2-DD0031?logo=angular&logoColor=white)](https://angular.dev/)
[![License](https://img.shields.io/badge/License-Unlicensed-lightgrey.svg)](LICENSE)

A cloud-native microservices digital banking system featuring service registration, centralized API routing, decoupled account and customer management, Model Context Protocol (MCP) servers with Spring AI, automated Telegram / Discord bots via Spring AI and JDA, and an Angular single-page application frontend.

---

## Table of Contents

- [About The Project](#about-the-project)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Backend Services Installation & Run](#backend-services-installation--run)
  - [Frontend Installation & Run](#frontend-installation--run)
- [Usage & Examples](#usage--examples)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## About The Project

`ebank-micro-services-app` is a distributed banking ecosystem built with Java, Spring Boot, Spring Cloud, Spring AI, and Angular.

The platform separates responsibilities across dedicated microservices:
- A service discovery registry that powers dynamic lookup and routing across the cluster.
- A reactive API gateway running on Spring Cloud Gateway WebFlux to provide a single entry point for client consumers.
- Domain services (`customer-service` and `ebank-service`) handling persistence via Spring Data JPA and in-memory H2 databases, exposing MCP (Model Context Protocol) endpoints for AI integration.
- An intelligent conversational bot service (`ebank-bot`) powered by Google GenAI (via Spring AI), Telegram Long Polling, and JDA (Discord integration), which talks to domain services using MCP client tools.
- A client-side Angular dashboard displaying account states and an embedded AI bot interaction interface.

---

## Key Features

- **Microservice Architecture**: Decoupled, single-responsibility services managed via Netflix Eureka discovery.
- **Dynamic API Gateway**: Centralized request handling and route filtering using Spring Cloud Gateway Server WebFlux.
- **Inter-Service Communication & Resilience**: Declarative HTTP clients using OpenFeign and resilience mechanisms powered by Resilience4j circuit breakers.
- **Spring AI & Model Context Protocol (MCP)**:
  - `customer-service` and `ebank-service` act as MCP webmvc servers (`spring-ai-starter-mcp-server-webmvc`).
  - `ebank-bot` consumes MCP tools via `spring-ai-starter-mcp-client-webflux` alongside Google GenAI models (`spring-ai-starter-model-google-genai`).
- **Multi-Platform Bot Integration**: Interactive bot support with long-polling Telegram bots (`telegrambots-springboot-longpolling-starter`) and Discord listeners via JDA (`net.dv8tion:JDA`).
- **API Documentation**: Interactive OpenAPI / Swagger UI provided out-of-the-box using Springdoc OpenAPI Starter.
- **Modern Angular Frontend**: Angular 21 application built with TypeScript, standalone router architecture, Bootstrap 5 UI, markdown rendering (`ngx-markdown`), and Vitest test runner.

---

## Tech Stack

### Backend
- **Languages**: Java 21 / 27
- **Framework**: Spring Boot (v3.4.1 / v4.1.1), Spring Cloud (2024.0.0 / 2025.1.3)
- **Service Discovery**: Spring Cloud Netflix Eureka Server & Client
- **API Gateway**: Spring Cloud Gateway Server WebFlux
- **Persistence & ORM**: Spring Data JPA, H2 In-Memory Database
- **AI & Integrations**: Spring AI (v1.1.8 / v2.0.1), Google GenAI, Spring AI MCP (Server WebMVC / Client WebFlux)
- **Bot Clients**: Telegram Bots Spring Boot Starter v8.3.0, JDA v5.6.1
- **API Tooling**: Springdoc OpenAPI WebMVC UI
- **Build Tool**: Apache Maven

### Frontend
- **Framework**: Angular v21.2
- **Language**: TypeScript v5.9
- **Styling**: Bootstrap v5.3, Bootstrap Icons v1.13, Custom CSS
- **Markdown**: `ngx-markdown` v21.3
- **Test Runner**: Vitest v4.0 with JSDOM
- **Package Manager**: npm

---

## Project Structure

```text
ebank-micro-services-app/
├── pom.xml                               # Root aggregator Maven project
├── discovery-service/                    # Netflix Eureka discovery registry
│   └── pom.xml
├── gateway-service/                      # Spring Cloud Gateway WebFlux reverse proxy
│   └── pom.xml
├── customer-service/                     # Customer domain service & MCP server
│   └── pom.xml
├── ebank-service/                        # Accounts & banking operations service & MCP server
│   └── pom.xml
├── ebank-bot/                            # AI bot service (Google GenAI, MCP Client, Telegram, JDA)
│   └── pom.xml
└── angular-front/                        # Angular 21 single-page application
    ├── angular.json
    ├── package.json
    ├── tsconfig.json
    ├── tsconfig.app.json
    ├── tsconfig.spec.json
    └── src/
        ├── index.html
        ├── main.ts
        ├── styles.css
        └── app/
            ├── app.config.ts
            ├── app.routes.ts
            ├── app.ts
            ├── app.html
            ├── app.css
            ├── accounts/                # Accounts view and components
            │   ├── accounts.ts
            │   ├── accounts.html
            │   └── accounts.css
            ├── bot-ui/                  # AI assistant chat interface
            │   ├── bot-ui.ts
            │   ├── bot-ui.html
            │   └── bot-ui.css
            └── interceptors/            # HTTP and loading state interceptors
```

---

## Environment Variables

The microservices utilize configuration driven by application properties or external environment variables. Depending on which bot integrations and external services are enabled, configure the following variables:

| Variable | Description | Target Service |
| :--- | :--- | :--- |
| `SPRING_AI_GOOGLE_GENAI_API_KEY` | Google Gemini / GenAI API key for LLM tool invocation | `ebank-bot` |
| `TELEGRAM_BOT_TOKEN` | Bot authorization token issued by Telegram BotFather | `ebank-bot` |
| `TELEGRAM_BOT_USERNAME` | Registered Telegram bot username | `ebank-bot` |
| `DISCORD_BOT_TOKEN` | Bot authentication token from Discord Developer Portal | `ebank-bot` |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | URL of the Eureka Discovery Registry | Domain & Gateway Services |
| `SPRING_CONFIG_IMPORT` | Centralized Spring Cloud Config Server URL (optional) | All backend services |

<!-- TODO: Configure specific details for additional custom profile properties -->

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: JDK 21 minimum (JDK 27 supported for services configured with Spring Boot 4.x).
- **Apache Maven**: Version 3.8+ or higher.
- **Node.js & npm**: Node.js v20+ and npm 11+.

---

### Backend Services Installation & Run

Backend microservices must be started in an order that ensures discovery and configuration registration are online before downstream consumers connect.

#### 1. Compile the Entire Repository

From the root directory:

```bash
mvn clean install -DskipTests
```

#### 2. Start Services in Sequential Order

Open distinct terminal windows or execute each service:

**Step A — Start Discovery Service**
```bash
cd discovery-service
mvn spring-boot:run
```
*Eureka Dashboard runs by default at `http://localhost:8761`.*

**Step B — Start Domain Services**
```bash
# In a new terminal:
cd customer-service
mvn spring-boot:run
```

```bash
# In a new terminal:
cd ebank-service
mvn spring-boot:run
```

**Step C — Start the Gateway Service**
```bash
cd gateway-service
mvn spring-boot:run
```

**Step D — Start the AI Bot Service**
```bash
cd ebank-bot
mvn spring-boot:run
```

---

### Frontend Installation & Run

#### 1. Install Dependencies

Navigate into `angular-front`:

```bash
cd angular-front
npm install
```

#### 2. Start the Development Server

```bash
npm run start
# or: ng serve
```

Navigate to `http://localhost:4200/` in your browser. The frontend will dynamically reload when changes to source files are saved.

To compile production assets:

```bash
npm run build
```
Production bundles will be written to `angular-front/dist/`.

---

## Usage & Examples

### Accessing Interactive OpenAPI / Swagger Documentation

When running services locally, inspect exposed REST and MCP interfaces:
- **Customer Service**: `http://localhost:8056/swagger-ui.html`
- **E-Bank Service**: `http://localhost:8057/swagger-ui.html`
- **E-Bank Bot**: `http://localhost:8058/swagger-ui.html`

### Interacting with the AI Bot

1. **Angular Dashboard**:
   - Access `http://localhost:4200/` and open the Chat / Bot UI component to issue natural language queries regarding customer details or bank accounts.
2. **Telegram**:
   - Send commands or direct messages to your registered Telegram bot handle. The bot utilizes Spring AI with MCP client tools to query live balance and profile endpoints.
3. **Discord**:
   - Once the JDA client is authenticated via token, invoke the bot within permitted Discord server channels.

---

## Testing

### Backend Unit & Integration Tests

Run Spring Boot slice and context tests across the parent module or individual microservices:

```bash
# Run tests for all Maven sub-modules:
mvn test

# Run tests for a specific microservice:
mvn -pl ebank-service test
```
## Screenshots

### Service Discovery (Eureka Dashboard)
<img width="1873" height="286" alt="Screenshot 2026-10-12 141002" src="https://github.com/user-attachments/assets/0c927c76-4d12-4ae4-a786-f4b4a10534e9" />
Eureka dashboard showing all registered microservices (Customer, Ebank, Gateway, Bot).

### API Gateway & REST APIs
<img width="565" height="547" alt="Screenshot 2026-10-12 141017" src="https://github.com/user-attachments/assets/f43cb17c-efa5-4ea5-9a96-14452b5a49ff" />

JSON response from the Customer Service via the API Gateway.

<img width="592" height="1027" alt="Screenshot 2026-10-12 141028" src="https://github.com/user-attachments/assets/3907a388-5578-469a-a10b-d78b523e4398" />

JSON response from the Ebank Service via the API Gateway.

### API Documentation (Swagger UI)
<img width="1853" height="330" alt="Screenshot 2026-10-12 140939" src="https://github.com/user-attachments/assets/0890c242-0768-4e24-8bd9-2e50ee4de04f" />

Swagger UI documenting the Customer REST controller endpoints.

<img width="1816" height="306" alt="Screenshot 2026-10-12 140949" src="https://github.com/user-attachments/assets/eee1ae3e-02d9-45a6-88b2-a18a44913d2d" />

Swagger UI documenting the Ebank REST controller endpoints.

<img width="1827" height="927" alt="Screenshot 2026-10-12 141101" src="https://github.com/user-attachments/assets/0e0a4b65-3e9d-4711-9add-b70a284eb6b5" />

Swagger UI documenting the Ebank Chatbot controller and /chat endpoint.

### AI Bot Integrations (Discord)
<img width="1362" height="773" alt="Screenshot 2026-10-12 230743" src="https://github.com/user-attachments/assets/c8175a32-b3ce-42e1-9d05-991566a9dbf7" />

Discord bot responding to a user query regarding accounts for a specific customer ID.

<img width="405" height="235" alt="Screenshot 2026-10-12 194127" src="https://github.com/user-attachments/assets/9ff26bbd-8c54-4f73-8930-3082655b55ad" />

Discord bot responding to a user query listing all registered customers.

### Angular Frontend Dashboard
<img width="1915" height="961" alt="Screenshot 2026-10-12 212036" src="https://github.com/user-attachments/assets/873513ac-8771-42f1-b276-2e27ae8dd019" />

Angular 21 SPA displaying the accounts list, integrated with the API Gateway and styled with Bootstrap.

Unit tests are written with Vitest and JSDOM:

```bash
cd angular-front
npm run test
```

---

## Contributing

1. **Fork the repository** (`git fork`).
2. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Commit your changes**:
   ```bash
   git commit -m "feat: implement feature description"
   ```
4. **Push to the branch**:
   ```bash
   git push origin feature/your-feature-name
   ```
5. **Open a Pull Request** against the `main` branch.

---

## License

This project is currently marked as **Unlicensed**. Please refer to the repository owner for access, distribution, and contribution rights.
