# BFinance — Domain Model

**Versão:** 1.0

## 1. Objetivo

Definir as entidades, seus relacionamentos e as principais regras de domínio do BFinance.

## 2. Entidades

### 2.1. User

Representa o usuário autorizado a acessar o sistema.

| Atributo | Tipo |
|---|---|
| id | UUID |
| email | Email |
| passwordHash | String |

**Regras:**
- O e-mail deve ser válido e único.
- A senha deve ser armazenada como hash.
- Não haverá cadastro público no MVP.

### 2.2. RefreshSession

Representa uma sessão autenticada com renovação por refresh token rotativo.

| Atributo | Tipo |
|---|---|
| id | UUID |
| userId | UUID |
| expiresAt | Instant |
| revokedAt | Instant (opcional) |

**Regras:**
- Uma sessão pertence a um usuário.
- Sessões expiradas ou revogadas não podem ser renovadas.
- Cada refresh token pode ser utilizado uma única vez.
- A reutilização detectada de um token consumido revoga a sessão.

O registro dos tokens emitidos e consumidos será detalhado na arquitetura de autenticação.

### 2.3. Account

Representa uma conta financeira.

| Atributo | Tipo |
|---|---|
| id | UUID |
| name | String |
| type | AccountType |
| initialBalance | BigDecimal |
| archived | Boolean |

**Regras:**
- O saldo pode ser negativo.
- Contas arquivadas não aceitam novas movimentações.
- O saldo atual é calculado a partir do saldo inicial e das movimentações efetivas.

### 2.4. Category

Representa uma categoria de receita ou despesa.

| Atributo | Tipo |
|---|---|
| id | UUID |
| name | String |
| type | TransactionType |

**Regras:**
- Uma categoria pertence a apenas um tipo.
- Categorias vinculadas a transações não podem ser excluídas.

### 2.5. Transaction

Representa uma receita ou despesa.

| Atributo | Tipo |
|---|---|
| id | UUID |
| description | String |
| amount | BigDecimal |
| date | LocalDate |
| type | TransactionType |
| accountId | UUID |
| categoryId | UUID |

**Regras:**
- O valor deve ser positivo.
- A categoria deve ser compatível com o tipo da transação.
- Receitas aumentam o saldo; despesas diminuem.
- Transações futuras não afetam o saldo atual.

### 2.6. Transfer

Representa uma transferência entre contas.

| Atributo | Tipo |
|---|---|
| id | UUID |
| amount | BigDecimal |
| date | LocalDate |
| description | String (opcional) |
| sourceAccountId | UUID |
| destinationAccountId | UUID |

**Regras:**
- Origem e destino devem ser diferentes.
- Ambas as contas devem estar ativas no momento do registro.
- O valor deve ser positivo.
- Transferências não são receitas ou despesas.
- Transferências futuras não afetam os saldos atuais.

### 2.7. Investment

Representa um investimento controlado manualmente.

| Atributo | Tipo |
|---|---|
| id | UUID |
| name | String |
| initialAmount | BigDecimal |

**Regras:**
- O valor inicial não pode ser negativo.
- O saldo investido não pode ficar negativo.
- Não haverá cálculo automático de rendimentos.

### 2.8. InvestmentMovement

Representa uma aplicação ou resgate.

| Atributo | Tipo |
|---|---|
| id | UUID |
| investmentId | UUID |
| accountId | UUID |
| type | InvestmentMovementType |
| amount | BigDecimal |
| date | LocalDate |

**Regras:**
- O valor deve ser positivo.
- Aplicações reduzem o saldo da conta e aumentam o valor investido.
- Resgates reduzem o valor investido e aumentam o saldo da conta.
- O resgate não pode superar o saldo investido disponível.
- Movimentações futuras não afetam os saldos atuais.
- Aplicações e resgates não são receitas ou despesas.

## 3. Value Objects

| Value Object | Responsabilidade |
|---|---|
| Email | Validar e normalizar endereços de e-mail |

Outros value objects poderão ser introduzidos quando houver necessidade concreta.

## 4. Enumerações

**AccountType:** CHECKING, SAVINGS, CASH, OTHER.

**TransactionType:** INCOME, EXPENSE.

**InvestmentMovementType:** APPLICATION, REDEMPTION.

## 5. Relacionamentos

| Entidade | Relacionamento | Cardinalidade |
|---|---|---|
| User | RefreshSession | 1:N |
| Account | Transaction | 1:N |
| Category | Transaction | 1:N |
| Account (origem) | Transfer | 1:N |
| Account (destino) | Transfer | 1:N |
| Investment | InvestmentMovement | 1:N |
| Account | InvestmentMovement | 1:N |

Os relacionamentos entre módulos serão representados preferencialmente por identificadores, evitando dependências diretas entre entidades de domínio.

## 6. Cálculos financeiros

### Saldo da conta

Saldo = Saldo inicial + Receitas − Despesas + Transferências recebidas − Transferências enviadas − Aplicações + Resgates.

### Saldo investido

Saldo investido = Valor inicial + Aplicações − Resgates.

### Patrimônio total

Patrimônio = Saldo das contas + Saldo investido.

Somente movimentações com data anterior ou igual à data de referência participam dos cálculos.

## 7. Agregados de domínio

| Agregado | Raiz |
|---|---|
| User | User |
| RefreshSession | RefreshSession |
| Account | Account |
| Category | Category |
| Transaction | Transaction |
| Transfer | Transfer |
| Investment | Investment |
| InvestmentMovement | InvestmentMovement |

Os agregados são inicialmente independentes. Regras que envolvam múltiplos agregados serão coordenadas pelos casos de uso da aplicação, com transações de banco de dados quando necessário.