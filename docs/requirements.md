# BFinance — Requisitos do MVP

## 1. Objetivo

O BFinance é uma aplicação web de finanças pessoais destinada ao controle de contas, receitas, despesas, transferências e investimentos.

O sistema será utilizado inicialmente por um único usuário, com registros financeiros manuais.

## 2. Requisitos funcionais

### RF-001 — Gerenciar contas
- Cadastrar, consultar, editar e arquivar contas financeiras.
- Informar nome, tipo e saldo inicial.
- Consultar o saldo atualizado de cada conta.

### RF-002 — Gerenciar categorias
- Cadastrar, consultar, editar e excluir categorias.
- Classificar categorias como receita ou despesa.

### RF-003 — Gerenciar transações
- Registrar receitas e despesas.
- Editar, excluir e consultar transações.
- Informar descrição, valor, data, conta e categoria.
- Consultar o histórico de movimentações.

### RF-004 — Realizar transferências
- Transferir valores entre contas.
- Informar conta de origem, destino, valor e data.
- Consultar o histórico de transferências.

### RF-005 — Gerenciar investimentos
- Cadastrar e consultar investimentos.
- Registrar aplicações e resgates.
- Consultar o saldo aplicado em cada investimento.
- Consultar o histórico de aplicações e resgates.
- Permitir o cadastro de investimentos com valor inicial.

### RF-006 — Visualizar dashboard
- Consultar o saldo total das contas.
- Visualizar receitas e despesas do mês.
- Visualizar o resultado financeiro mensal.
- Consultar o total aplicado em investimentos.
- Visualizar movimentações recentes.

### RF-007 — Autenticação
- Autenticar o usuário por e-mail e senha.
- Permitir renovação da sessão.
- Permitir logout.
- Restringir o acesso às funcionalidades financeiras.

## 3. Requisitos não funcionais

- **RNF-001:** A interface deve ser responsiva e funcionar em navegadores modernos.
- **RNF-002:** O acesso aos dados financeiros deve exigir autenticação.
- **RNF-003:** Os valores monetários devem utilizar precisão decimal.
- **RNF-004:** As operações financeiras devem garantir consistência e atomicidade.
- **RNF-005:** O sistema deve apresentar valores em reais (BRL) e utilizar o padrão brasileiro de datas.

## 4. Regras de negócio

### Contas
- **RN-001:** O saldo de uma conta pode ser negativo.
- **RN-002:** Contas arquivadas mantêm seu histórico e não podem receber novas movimentações.

### Categorias
- **RN-003:** Uma categoria deve pertencer ao tipo receita ou despesa.
- **RN-004:** Categorias vinculadas a transações não podem ser excluídas.

### Transações
- **RN-005:** O valor de uma transação deve ser maior que zero.
- **RN-006:** Receitas aumentam o saldo da conta; despesas diminuem.
- **RN-007:** Transações futuras só afetam o saldo na data programada.

### Transferências
- **RN-008:** Transferências devem ocorrer entre contas diferentes e ativas.
- **RN-009:** Transferências reduzem o saldo da origem e aumentam o saldo do destino, sem representar receitas ou despesas.
- **RN-010:** Transferências futuras só afetam os saldos na data programada.

### Investimentos
- **RN-011:** Aplicações reduzem o saldo da conta de origem e aumentam o saldo investido.
- **RN-012:** Resgates reduzem o saldo investido e aumentam o saldo da conta de destino.
- **RN-013:** Aplicações e resgates não são classificados como receitas ou despesas.
- **RN-014:** O valor resgatado não pode superar o saldo disponível do investimento.
- **RN-015:** Investimentos existentes podem ser cadastrados com valor inicial, sem movimentar contas financeiras.
- **RN-016:** Aplicações e resgates futuros só afetam os saldos na data programada.

## 5. Fora do escopo do MVP

- Cartões de crédito e faturas.
- Integrações bancárias e com corretoras.
- Rendimentos, rentabilidade e valorização de investimentos.
- Cotações automáticas de ativos.
- Controle de investimentos por quantidade de cotas.
- Orçamentos e metas financeiras.
- Transações recorrentes automáticas.
- Relatórios financeiros avançados.
- Cadastro público e gerenciamento de múltiplos usuários.