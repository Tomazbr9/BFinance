# ADR-001 — Monólito Modular

**Status:** Aceita

## Contexto
O BFinance é uma aplicação de finanças pessoais que precisa de organização modular sem a complexidade operacional de microsserviços.

## Decisão
Adotar um monólito modular utilizando Spring Boot, com módulos separados por responsabilidades de negócio.

## Consequências
- Facilita o desenvolvimento, os testes e o deploy.
- Permite evolução independente da organização interna dos módulos.
- Exige disciplina para evitar acoplamento excessivo entre módulos.