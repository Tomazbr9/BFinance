# ADR-005 — Conteinerização com Docker Compose

**Status:** Aceita

## Contexto
O BFinance precisa de um ambiente de execução padronizado que facilite o desenvolvimento, a configuração e a implantação da aplicação, reduzindo diferenças entre ambientes.

## Decisão
Adotar Docker e Docker Compose para conteinerizar o backend Spring Boot e o banco de dados PostgreSQL.

- **Backend:** executado em um contêiner construído a partir de um Dockerfile.
- **PostgreSQL:** executado em um contêiner com volume persistente.
- **Comunicação:** realizada pela rede interna do Docker Compose.
- **Configurações:** fornecidas por variáveis de ambiente, sem versionar credenciais ou segredos.
- **Inicialização:** utilizar healthcheck para verificar a disponibilidade do banco antes de iniciar o backend.

O frontend Angular será executado separadamente durante o desenvolvimento.

## Consequências
- Padroniza o ambiente de execução.
- Simplifica a configuração e a implantação dos serviços.
- Facilita a reprodução do ambiente em diferentes máquinas.
- Permite persistir os dados independentemente do ciclo de vida dos contêineres.
- Exige conhecimento básico de Docker e manutenção dos arquivos de configuração.
- Adiciona uma camada de complexidade operacional ao desenvolvimento local.