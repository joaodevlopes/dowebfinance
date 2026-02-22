# 💰 Dowebfinance - Gestão Financeira Pessoal

Este é um projeto desenvolvido para fins acadêmicos e de portfólio, com o objetivo de criar uma solução robusta para organização e controle de finanças pessoais. 

O foco principal é o aprofundamento no ecossistema **Java/Spring** e a aplicação prática de segurança, persistência de dados e containerização.

## 🛠️ Tecnologias e Ferramentas
* **Back-end:** Java 21, Spring Boot 3, Spring Data JPA, Spring Security.
* **Banco de Dados:** PostgreSQL (Persistência robusta e relacional).
* **Front-end:** HTML5, CSS3 e JavaScript (Vanilla).
* **Build & Dependency Management:** Maven.

## 🚀 Roadmap de Desenvolvimento
Este projeto serve como laboratório para a implementação de tecnologias cruciais de mercado:
- [x] Estrutura Inicial e CRUD de Usuários.
- [x] Migração de banco de dados para PostgreSQL.
- [ ] Implementação de Autenticação e Autorização (Spring Security + JWT).
- [ ] **Docker:** Containerização da aplicação e do banco de dados para facilitar o deploy.
- [ ] Implementação de Testes Unitários e de Integração (JUnit 5, Mockito).

## 🏗️ Arquitetura e Boas Práticas
O projeto segue princípios sólidos para garantir escalabilidade:
- **Clean Code:** Código legível e focado em manutenção simplificada.
- **RESTful API:** Design de endpoints seguindo os padrões e métodos HTTP.
- **Segurança:** Proteção de dados sensíveis e criptografia de senhas (BCrypt).

## 🔧 Como executar o projeto (Local)
1. Clone o repositório.
2. Certifique-se de ter o **PostgreSQL** rodando localmente.
3. Crie um arquivo `src/main/resources/application.properties` baseado no `application.properties.example` fornecido.
4. Execute via Maven: `./mvnw spring-boot:run`

---
*Desenvolvido por João Gabriel Lima Lopes*
