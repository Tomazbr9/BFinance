# BFinance — Arquitetura de Software

**Versão:** 1.0  
**Estilo:** Monólito Modular + Clean Architecture + DDD

## 1. Visão geral

O BFinance será desenvolvido como um monólito modular, organizado por funcionalidades de negócio.

Cada módulo seguirá os princípios da Clean Architecture, utilizando DDD para modelagem do domínio.

**Princípios:**
- Baixo acoplamento entre módulos.
- Domínio independente de frameworks.
- Separação entre domínio e persistência JPA.
- Dependências direcionadas para as camadas internas.
- Simplicidade e testabilidade.

## 2. Tecnologias

| Componente | Tecnologia |
|---|---|
| Backend | Java + Spring Boot |
| Frontend | Angular |
| Banco de dados | PostgreSQL |
| Persistência | Spring Data JPA |
| Segurança | Spring Security + JWT |
| Conteinerização | Docker + Docker Compose |
| Testes | JUnit, Mockito e Testcontainers |
| Build | Maven |

## 3. Módulos

| Módulo | Responsabilidade |
|---|---|
| identity | Autenticação, usuário e sessões |
| accounts | Contas financeiras |
| categories | Categorias financeiras |
| transactions | Receitas, despesas e transferências |
| investments | Investimentos, aplicações e resgates |
| dashboard | Consultas e resumos financeiros |

Todos os módulos serão executados na mesma aplicação Spring Boot, utilizando um banco PostgreSQL compartilhado.

## 4. Estrutura do projeto

```text
com.bfinance
├── BFinanceApplication
├── identity
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
├── accounts
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
├── categories
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
├── transactions
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
├── investments
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
├── dashboard
│   ├── application
│   ├── infrastructure
│   └── presentation
└── shared
```

### Domain

Contém entidades, value objects, regras de negócio e contratos de repositório.

Não depende de Spring, JPA ou infraestrutura.

### Application

Contém casos de uso, contratos necessários e coordenação das regras de negócio.

Depende do domínio, mas não dos detalhes de infraestrutura ou apresentação.

### Infrastructure

Contém implementações técnicas:
- Entidades JPA e mapeadores.
- Repositórios Spring Data JPA.
- Implementações dos contratos de persistência.
- Configurações de segurança e integrações externas.

### Presentation

Responsável pela comunicação HTTP com o frontend:
- REST Controllers.
- DTOs de requisição e resposta.
- Validação dos dados recebidos.
- Tratamento e padronização de erros HTTP.

A Presentation utiliza os casos de uso da Application e não acessa diretamente os repositórios de persistência.

## 5. Comunicação entre módulos

- A comunicação deve ocorrer por contratos públicos de aplicação.
- Um módulo não deve acessar os repositórios internos de outro.
- Entidades de domínio não devem ser compartilhadas entre módulos.
- Relacionamentos entre módulos utilizam preferencialmente identificadores.
- Não utilizar chamadas HTTP internas.
- Evitar dependências circulares.

## 6. Persistência

O PostgreSQL será compartilhado pelos módulos.

**Diretrizes:**
- Entidades de domínio independentes das entidades JPA.
- Mapeamentos realizados na Infrastructure.
- Repositórios definidos por contratos.
- Operações financeiras devem garantir consistência transacional.
- Saldos calculados a partir das movimentações efetivas.
- Migrações do banco versionadas.

## 7. Autenticação

A autenticação será implementada no módulo `identity`, utilizando Spring Security.

### Access Token

- JWT assinado pelo backend.
- Duração inicial de 15 minutos.
- Utilizado para acessar endpoints protegidos.

### Refresh Token

- Token opaco e criptograficamente seguro.
- Duração inicial de 7 dias.
- Armazenado no banco apenas como hash.
- Enviado em cookie HttpOnly e Secure.
- Rotacionado a cada renovação.

**Regras:**
- O token anterior é invalidado após a rotação.
- A reutilização detectada revoga a sessão.
- A rotação deve ser atômica.
- O logout revoga a sessão.
- Endpoints autenticados por cookies devem utilizar proteção adequada contra CSRF.

## 8. API REST

O Angular se comunicará com o backend por HTTP e JSON.

| Módulo | Prefixo |
|---|---|
| Identity | /api/auth |
| Accounts | /api/accounts |
| Categories | /api/categories |
| Transactions | /api/transactions |
| Transfers | /api/transfers |
| Investments | /api/investments |
| Dashboard | /api/dashboard |

## 9. Conteinerização

O projeto utilizará Docker Compose para executar o backend e o PostgreSQL.

### Serviços

| Serviço | Responsabilidade |
|---|---|
| backend | Executar a aplicação Spring Boot |
| postgres | Armazenar os dados financeiros |

**Diretrizes:**
- O backend será construído a partir de um Dockerfile.
- O PostgreSQL utilizará um volume persistente.
- A comunicação entre serviços ocorrerá pela rede interna do Docker Compose.
- As configurações serão fornecidas por variáveis de ambiente.
- Senhas e segredos não serão versionados no repositório.
- O backend deverá aguardar a disponibilidade do banco por meio de healthcheck e configuração apropriada de dependência.
- O PostgreSQL não precisará expor sua porta publicamente em produção.

### Organização

```text
BFinance/
├── backend/
│   ├── Dockerfile
│   └── ...
├── frontend/
│   └── ...
├── docs/
├── compose.yaml
├── .env.example
└── README.md
```

### Execução

```bash
docker compose up -d --build
```

O Angular poderá ser executado separadamente durante o desenvolvimento.

## 10. Testes

**Unitários:**
- Entidades e value objects.
- Casos de uso.
- Regras financeiras.
- Autenticação e renovação.

**Integração:**
- Persistência PostgreSQL.
- Operações financeiras atômicas.
- Segurança dos endpoints.
- Rotação e reutilização de refresh tokens.
- Comunicação entre módulos.

## 11. Diretrizes de implementação

- Aplicar DDD de forma pragmática.
- Evitar abstrações desnecessárias.
- Manter regras de negócio no domínio.
- Utilizar casos de uso para coordenar operações.
- Não utilizar microsserviços no MVP.
- Não introduzir CQRS ou Event Sourcing.
- Respeitar os limites entre módulos e camadas.