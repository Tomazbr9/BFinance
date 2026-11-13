# ADR-004 — Autenticação JWT com Refresh Token Rotativo

**Status:** Aceita

## Contexto
O BFinance precisa de um mecanismo de autenticação seguro que permita controlar o acesso aos dados financeiros e renovar sessões sem exigir login frequente.

## Decisão
Adotar autenticação com Spring Security, utilizando JWT para access tokens e refresh tokens opacos com rotação.

- **Access Token:** JWT assinado, com validade inicial de 15 minutos.
- **Refresh Token:** token aleatório, com validade inicial de 7 dias.
- **Armazenamento:** refresh tokens armazenados no banco apenas como hash e enviados ao navegador em cookies HttpOnly e Secure.
- **Rotação:** cada renovação invalida o refresh token anterior e emite um novo.
- **Segurança:** reutilizações detectadas de tokens consumidos revogam a sessão; a rotação deve ser atômica.
- **Logout:** revoga a sessão, impedindo novas renovações.

Os endpoints que utilizam cookies deverão possuir proteção adequada contra CSRF.

## Consequências
- Permite renovar sessões sem solicitar credenciais frequentemente.
- Reduz a exposição dos access tokens por meio de sua curta duração.
- Possibilita revogar sessões e detectar reutilização de refresh tokens.
- Exige persistência e controle de concorrência na rotação.
- Access tokens já emitidos podem continuar válidos até sua expiração.
- Aumenta a complexidade em comparação com autenticação sem renovação de sessão.