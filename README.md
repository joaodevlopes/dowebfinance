# 💰 Dowebfinance - Gestão Financeira Pessoal

API REST para organização e controle de finanças pessoais, desenvolvida para fins de estudo e portfólio.

O projeto começou como laboratório de **Java/Spring** (segurança, persistência e boas práticas) e está evoluindo para um produto completo: controle das despesas do mês, metas de investimento com taxas reais e recomendações de quanto ainda dá para gastar.

## 🛠️ Tecnologias e Ferramentas
* **Back-end:** Java 21, Spring Boot 4, Spring Data JPA, Spring Security, Bean Validation.
* **Autenticação:** JWT (JJWT 0.12) stateless, senhas com BCrypt.
* **Mapeamento:** MapStruct (DTO ↔ entidade) e Lombok.
* **Banco de Dados:** PostgreSQL.
* **Build & Dependency Management:** Maven.
* **Front-end (planejado):** React + TypeScript + Tailwind.

## ✅ O que já funciona
- Cadastro de usuário com senha criptografada e verificação de e-mail duplicado.
- Login que devolve um token JWT válido por 2 horas.
- CRUD de lançamentos (entradas e saídas) por categoria, data e banco.
- **Isolamento por usuário:** o dono de cada recurso vem do token, nunca da requisição, então nenhum usuário acessa dados de outro.
- Respostas de erro padronizadas: `400` para validação, `401` para login ou token inválido, `404` para recurso inexistente.

## 📡 Endpoints

| Método | Rota | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/api/users` | Pública | Cadastra um usuário |
| `POST` | `/api/auth/login` | Pública | Faz login e devolve o token |
| `GET` | `/api/users/me` | Token | Dados do usuário logado |
| `PUT` | `/api/users/me` | Token | Atualiza o usuário logado |
| `DELETE` | `/api/users/me` | Token | Apaga a conta do usuário logado |
| `POST` | `/api/expenses` | Token | Cria um lançamento |
| `GET` | `/api/expenses` | Token | Lista os lançamentos do usuário |
| `GET` | `/api/expenses/{id}` | Token | Busca um lançamento |
| `PUT` | `/api/expenses/{id}` | Token | Edita um lançamento |
| `DELETE` | `/api/expenses/{id}` | Token | Apaga um lançamento |

Rotas com token exigem o header `Authorization: Bearer <token>`.

**Exemplo de lançamento:**
```json
{
  "description": "Mercado",
  "value": 250.90,
  "type": "SAIDA",
  "category": "ALIMENTACAO",
  "bankName": "Nubank",
  "expenseDate": "2026-10-10"
}
```
`type`: `ENTRADA` ou `SAIDA`. `category`: `ALIMENTACAO`, `MORADIA`, `LAZER`, `SALARIO`, `TRANSPORTE`, `INVESTIMENTO` ou `OUTROS`.

## 🚀 Roadmap
**Base**
- [x] Estrutura inicial e CRUD de usuários e lançamentos.
- [x] Migração do banco de dados para PostgreSQL.
- [x] Autenticação e autorização com Spring Security + JWT.
- [x] Isolamento de dados por usuário.
- [ ] Testes unitários e de integração (JUnit 5, Mockito).
- [ ] Migrações de banco com Flyway.
- [ ] **Docker:** containerização da aplicação e do banco.

**Produto**
- [ ] **Despesas do mês:** contas recorrentes, status pago/pendente/atrasado, categorias personalizadas.
- [ ] **Metas de investimento:** taxas Selic, CDI e IPCA da API do Banco Central, projeção em gráfico, "quanto aportar" e "quando chego".
- [ ] **Quanto posso gastar:** saldo disponível no mês, gasto seguro por dia e recomendações por regras (50/30/20, reserva de emergência).
- [ ] **Cartão de crédito:** faturas e parcelamento.
- [ ] Front-end em React e deploy com conta demo.

## 🏗️ Arquitetura e Boas Práticas
- **Camadas:** controller → service → repository, com DTOs na borda da API (entidades nunca são expostas).
- **RESTful API:** endpoints seguindo os padrões e métodos HTTP.
- **Segurança:** identidade sempre vinda do token, senhas com BCrypt, segredos fora do repositório (variáveis de ambiente).
- **Tratamento de erros centralizado** com `@RestControllerAdvice`.

## 🔧 Como executar o projeto (Local)
**Pré-requisitos:** Java 21, Maven e PostgreSQL.

1. Clone o repositório.
2. Crie o usuário e o banco do projeto no PostgreSQL:
   ```sql
   CREATE USER dowebfinance WITH PASSWORD 'dowebfinance';
   CREATE DATABASE dowebfinance OWNER dowebfinance;
   ```
3. Copie `dowebfinance/src/main/resources/application.properties.example` para `application.properties`, na mesma pasta. Os valores padrão já funcionam localmente. Em produção, defina as variáveis de ambiente `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `JWT_SECRET`.
4. Na pasta `dowebfinance/`, execute `mvn spring-boot:run` (ou rode a classe `DowebfinanceApplication` pela IDE).
5. A API sobe em `http://localhost:8080`.

---
*Desenvolvido por João Gabriel Lima Lopes*
