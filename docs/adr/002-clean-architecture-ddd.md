# ADR-002 — Clean Architecture e DDD

**Status:** Aceita

## Contexto
O BFinance precisa de uma estrutura que separe as regras de negócio dos detalhes técnicos, facilitando a manutenção, os testes e a evolução dos módulos.

## Decisão
Adotar Clean Architecture e DDD de forma pragmática, organizando cada módulo em quatro camadas:

- **Domain:** entidades, value objects e regras de negócio.
- **Application:** casos de uso e coordenação das operações.
- **Infrastructure:** persistência, segurança e implementações técnicas.
- **Presentation:** controllers REST, DTOs e tratamento de requisições.

As dependências devem apontar para as camadas internas, mantendo o domínio independente de frameworks.

## Consequências
- Facilita o isolamento e os testes das regras de negócio.
- Melhora a organização e a manutenção do código.
- Permite substituir detalhes técnicos sem afetar o domínio.
- Exige mais classes e disciplina na separação das responsabilidades.
- Abstrações desnecessárias podem aumentar a complexidade do projeto.