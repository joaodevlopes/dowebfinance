# NEXT — onde parei e o que vem depois

Arquivo para retomar o projeto rápido depois de uma pausa. Atualize sempre que parar.

**Última atualização:** 10/10/2026
**Status:** pausado para focar no projeto de dados. Retomar depois.

## Onde parei

- Projeto compila. Sessão 1 (deixar a base sólida) ficou quase completa.
- Correções feitas, cada uma no seu commit (`git log` para ver o motivo de cada uma):
  1. `ExpenseMapper` sem o `userId` que não existe mais (o projeto não compilava).
  2. `/api/users/{id}` virou `/api/users/me` (qualquer usuário mexia na conta de outro, falha IDOR).
  3. Token inválido ou expirado responde 401 (antes estourava exceção no `JwtFilter`).
  4. Login com senha errada responde 401 (antes caía no handler genérico e dava 500).
  5. `@Data` trocado por `@Getter`/`@Setter` nas entidades (risco de StackOverflow na relação User ↔ Expense).
  6. `application.properties.example` completo, com o secret do JWT e lendo variáveis de ambiente.
- README atualizado.
- **Ainda não testado de ponta a ponta.** O comando que cria o usuário e o banco `dowebfinance` no PostgreSQL 18 do Windows foi rodado, mas a API ainda não foi exercitada.

## Como vou trabalhar ao voltar

**Eu escrevo o código. O Claude explica, dá pistas e revisa.** O objetivo é reaprender Java/Spring e conseguir defender cada linha numa entrevista.

## Para reaquecer o Java (fazer primeiro)

- [ ] Ler os 6 commits de correção com `git show <hash>`, um por vez, e explicar com minhas palavras o que mudou e por quê:
      `dd0d200`, `64199cb`, `cef39f4`, `6a0a492`, `3931a7a`, `7a9daae`.
- [ ] Remover o `UserService` injetado sem uso no `ExpenseService` (relembrar injeção por construtor).

## Próximos passos

**Terminar a Sessão 1**
- [ ] Subir a API e testar com `curl` (ou Postman): cadastro, login, login errado (401), token falso (401), CRUD de lançamento, tentar acessar o lançamento de outro usuário (404).

**Sessão 2: preparar o terreno**
- [ ] Primeiro teste unitário do `ExpenseService` (JUnit 5 + Mockito), começando pelo isolamento por usuário.
- [ ] Renomear o pacote `com.example` para `com.joaogabriel`.
- [ ] Reorganizar o repositório em `backend/` e `frontend/`, e criar um `.gitignore` na raiz (a pasta `.idea/` da raiz aparece como não rastreada; **não usar `git add .`** até resolver).
- [ ] Trocar `ddl-auto=update` por Flyway.
- [ ] `docker-compose.yml` com PostgreSQL (precisa instalar o Docker Desktop). O comentário do `.properties.example` já cita esse arquivo, que ainda não existe.
- [ ] `ExpenseResponseDTO` devolve o usuário inteiro em cada lançamento: avaliar remover.
- [ ] Configurar CORS (o front vai precisar).

**Depois: produto**
1. MVP: categorias próprias, contas recorrentes, status pago/pendente/atrasado, dashboard do mês, primeira tela em React.
2. Metas de investimento: API SGS do Banco Central (Selic `432`, CDI `4389`, IPCA `433`), projeção e gráfico.
3. Quanto posso gastar: saldo disponível e recomendações por regras.
4. Cartão de crédito: faturas e parcelamento.
5. Polimento: deploy, conta demo, README com GIF.
