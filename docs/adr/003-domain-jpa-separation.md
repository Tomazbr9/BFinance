# ADR-003 — Separação entre Domínio e JPA

**Status:** Aceita

## Contexto
O BFinance utiliza Clean Architecture e DDD, exigindo que as regras de negócio permaneçam independentes dos detalhes de persistência e dos frameworks utilizados.

## Decisão
Manter as entidades de domínio separadas das entidades JPA.

- **Domain:** contém entidades puras, sem anotações de persistência.
- **Infrastructure:** contém entidades JPA, repositórios Spring Data e mapeadores.
- **Application:** utiliza contratos de repositório definidos nas camadas internas, sem depender diretamente do JPA.

A conversão entre entidades de domínio e entidades JPA será realizada por mapeadores na camada Infrastructure.

## Consequências
- Mantém o domínio independente do Spring Data JPA.
- Facilita os testes das regras de negócio sem banco de dados.
- Permite alterar a estratégia de persistência sem modificar o domínio.
- Exige classes e mapeamentos adicionais.
- Aumenta o trabalho de manutenção quando as entidades evoluem.