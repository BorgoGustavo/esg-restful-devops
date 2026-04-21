# Projeto - Cidades ESG Inteligentes

API REST em **Java 21 / Spring Boot 3.4.5** que administra indicadores sociais
e de diversidade (% mulheres, negros, PCDs, LGBTQIA+) por departamento,
colaboradores, cargos, departamentos e programas de treinamento — o pilar
**Social** do ESG aplicado ao conceito de _Cidades ESG Inteligentes_.

Este repositório é a entrega do desafio **"Navegando pelo mundo DevOps"**:
a aplicação foi adaptada para produção com **containerização**, **orquestração
multi-ambiente** e **pipeline CI/CD** automatizado.

## Integrantes

| Nome | RM |
|------|----|
| _(preencher)_ | _(preencher)_ |

---

## Como executar localmente com Docker

Pré-requisitos: **Docker Desktop** (ou Docker Engine + Compose v2).

```bash
# 1. Clonar o repositório
git clone <url-do-repo>
cd esg-restful

# 2. Criar o arquivo .env a partir do exemplo (staging)
cp .env.example .env

# 3. Subir a stack de staging (app + Postgres)
docker compose -f docker-compose.staging.yml up -d --build

# 4. Aguardar o healthcheck ficar verde (primeira vez ~60s)
docker compose -f docker-compose.staging.yml ps

# 5. Validar
curl http://localhost:8080/actuator/health
# -> {"status":"UP"}
```

### Subir staging e produção em paralelo

Os dois ambientes foram isolados em **networks, volumes e portas** diferentes,
então podem rodar ao mesmo tempo na mesma máquina:

| Ambiente | App | Postgres | Rede | Volume |
|----------|-----|----------|------|--------|
| staging  | `localhost:8080` | `localhost:5432` | `esg-staging` | `pgdata-staging` |
| produção | `localhost:8090` | `localhost:5433` | `esg-prod`    | `pgdata-prod`    |

```bash
# Staging (default)
docker compose -f docker-compose.staging.yml --env-file .env up -d

# Produção (em outra env, outras credenciais)
cp .env.example .env.prod
# edite .env.prod com HOST_APP_PORT=8090, HOST_DB_PORT=5433 e senhas de produção
docker compose -f docker-compose.prod.yml --env-file .env.prod up -d
```

### Usando a API

Todos os endpoints `/api/**` exigem token JWT. Fluxo completo:

```bash
# 1) Registrar usuário
curl -X POST http://localhost:8080/auth/registrar \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Admin","email":"admin@esg.local","senha":"admin123","role":"ADMIN"}'

# 2) Login -> token
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"admin@esg.local","senha":"admin123"}' | jq -r .token)

# 3) Chamar endpoint protegido
curl http://localhost:8080/api/colaboradores -H "Authorization: Bearer $TOKEN"
```

---

## Pipeline CI/CD

Ferramenta: **GitHub Actions**. Dois workflows em `.github/workflows/`:

### `ci.yml` — Integração Contínua

Disparado em **todo push** e **pull request** para `main`/`develop`.

| Job | O que faz |
|-----|-----------|
| `build-test` | Checkout → setup JDK 21 Temurin com cache Maven → `./mvnw test` no perfil **`test`** (H2 em memória, Flyway desativado) → publica relatórios surefire. |
| `package` | (depende de `build-test` verde) → `./mvnw package -DskipTests` → sobe o JAR como artifact do run. |

### `cd.yml` — Entrega Contínua

Disparado em **push para `main`** (e via `workflow_dispatch`).

| Job | O que faz |
|-----|-----------|
| `docker` | Buildx + login no **GHCR** (`ghcr.io/<owner>/<repo>`) → `docker/metadata-action` gera tags `sha-<7>` e `latest` → `build-push-action` com cache GitHub Actions. |
| `deploy-staging` | **environment: staging** → baixa a imagem recém-publicada → `docker compose up -d` do `docker-compose.staging.yml` → aguarda healthcheck `/actuator/health` → smoke test do endpoint público e do endpoint protegido (deve devolver 401/403) → publica logs. |
| `deploy-prod` | **environment: production** — exige **aprovação manual** configurada no GitHub → mesmo padrão do staging, mas em `docker-compose.prod.yml`, porta 8090, lendo `secrets.PROD_JWT_SECRET` / `secrets.PROD_DB_PASSWORD`. |

Lógica geral:

```
push main ──▶ CI (build + test + package) ──▶ CD (docker build + push GHCR)
                                                │
                                                ▼
                                     deploy-staging (auto)
                                                │
                                                ▼
                                     deploy-prod (manual gate)
```

### Configurar o pipeline pela primeira vez

1. Criar repositório no GitHub e dar push deste projeto.
2. Em **Settings → Environments**, criar dois _environments_:
   - `staging` (sem aprovação obrigatória)
   - `production` (marcar **"Required reviewers"** e adicionar o(s) revisor(es))
3. Em **production**, adicionar os secrets:
   - `PROD_JWT_SECRET`
   - `PROD_DB_PASSWORD`
4. O `GITHUB_TOKEN` do runner já tem permissão para publicar imagens no GHCR.

---

## Containerização

**Arquitetura:**

```
┌────────────────────────────────────────────────────────┐
│                      Host (Docker)                     │
│                                                        │
│   ┌───────────────────┐     ┌─────────────────────┐    │
│   │  app (Spring Boot)│────▶│ db (PostgreSQL 16)  │    │
│   │  JRE 21 Alpine    │ JDBC│ volume: pgdata-*    │    │
│   │  user non-root    │     │                     │    │
│   └───────────────────┘     └─────────────────────┘    │
│            ▲                                           │
│            │ 8080 (staging) / 8090 (prod)              │
└────────────┼───────────────────────────────────────────┘
             │
         host machine
```

**Dockerfile (multi-stage):**

```dockerfile
# Stage 1 — build com Maven + Temurin 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .mvn/ mvnw ./
RUN ./mvnw -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN ./mvnw -B -q -DskipTests package && mv target/esg-restful.jar /build/app.jar

# Stage 2 — runtime enxuto
FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache curl && addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
COPY --from=build /build/app.jar /app/app.jar
USER spring
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
HEALTHCHECK CMD curl -fsS http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
```

**Estratégias adotadas:**

- **Multi-stage** — imagem final com apenas JRE 21 + JAR (≈ 200 MB), sem Maven nem fontes.
- **Usuário não-root (`spring`)** — hardening básico de container.
- **`JAVA_OPTS=-XX:MaxRAMPercentage=75`** — JVM respeita os limites de memória do container.
- **Healthcheck nativo** via `/actuator/health` (Spring Boot Actuator liberado no `SecurityConfig`).
- **Cache de dependências** em camada separada (`dependency:go-offline` antes do `COPY src`).
- **`.dockerignore`** remove `target/`, `.git/`, `.idea/`, `docs/`, logs e `.env` do build context.

**docker-compose — uso de redes/volumes/env vars:**

- Dois arquivos (`docker-compose.staging.yml` e `docker-compose.prod.yml`) com `name:` distinto, **networks isoladas** (`esg-staging` vs `esg-prod`) e **volumes** nomeados (`pgdata-staging` vs `pgdata-prod`).
- Variáveis sensíveis lidas do `.env` (JWT_SECRET, DB_PASSWORD, etc.) — ver `.env.example`.
- `depends_on: service_healthy` — app só sobe depois que o Postgres responde ao `pg_isready`.

---

## Profiles Spring Boot

| Profile | Banco | Onde é usado | Flyway |
|---------|-------|--------------|--------|
| _default_ | Oracle FIAP | execução local (IDE) na rede da faculdade | `db/migration/oracle/` |
| `docker`  | PostgreSQL 16 (container) | staging e produção | `db/migration/postgresql/` |
| `test`    | H2 in-memory | CI/CD (GitHub Actions) | desativado (Hibernate `create-drop`) |

As migrations Oracle originais foram mantidas em `src/main/resources/db/migration/oracle/`
e reescritas em sintaxe PostgreSQL (`SERIAL`, `FILTER`, `plpgsql`) em
`src/main/resources/db/migration/postgresql/`.

---

## Prints do funcionamento

Os prints completos estão em [`docs/prints/`](docs/prints/) e também embutidos
na documentação técnica (`docs/documentacao.pdf`).

| # | Evidência | Arquivo |
|---|-----------|---------|
| 1 | CI verde (build + testes) | `docs/prints/01-ci-green.png` |
| 2 | CD verde (docker + deploy staging + deploy prod) | `docs/prints/02-cd-green.png` |
| 3 | Imagem publicada no GHCR | `docs/prints/03-ghcr.png` |
| 4 | `docker compose ps` staging | `docs/prints/04-staging-ps.png` |
| 5 | `docker compose ps` produção | `docs/prints/05-prod-ps.png` |
| 6 | Health endpoint (staging + prod) | `docs/prints/06-health.png` |
| 7 | Environments/approval gate | `docs/prints/07-environments.png` |

---

## Tecnologias utilizadas

- **Linguagem & Runtime:** Java 21 (Eclipse Temurin)
- **Framework:** Spring Boot 3.4.5 (Web, Data JPA, Security, Validation, Actuator)
- **Autenticação:** JWT (`com.auth0:java-jwt 4.5.0`)
- **Banco de dados:** PostgreSQL 16 (Docker) / Oracle (legado) / H2 (testes)
- **Migrations:** Flyway (drivers `flyway-database-postgresql` e `flyway-database-oracle`)
- **Build:** Maven 3.9 (Maven Wrapper)
- **Containerização:** Docker (multi-stage) + Docker Compose v2
- **CI/CD:** GitHub Actions + GitHub Container Registry (GHCR)
- **Testes:** JUnit 5, Spring Boot Test, Spring Security Test, MockMvc, H2

---

## Estrutura do repositório

```
esg-restful/
├── .github/workflows/
│   ├── ci.yml                    # build + testes
│   └── cd.yml                    # docker build/push + deploy staging/prod
├── src/
│   ├── main/java/br/com/fiap/esg_restful/  (controllers, services, models, config)
│   ├── main/resources/
│   │   ├── application.properties              (default = Oracle FIAP)
│   │   ├── application-docker.properties       (staging/prod = Postgres)
│   │   └── db/migration/
│   │       ├── oracle/V1..V4.sql               (scripts originais)
│   │       └── postgresql/V1..V4.sql           (equivalentes Postgres)
│   └── test/
│       ├── java/br/com/fiap/esg_restful/...    (contextLoads + health + security)
│       └── resources/application-test.properties (H2 in-memory)
├── Dockerfile                    # multi-stage
├── .dockerignore
├── docker-compose.yml            # alias para staging
├── docker-compose.staging.yml
├── docker-compose.prod.yml
├── .env.example
├── README.md                     # este arquivo
├── CHECKLIST.md                  # checklist obrigatório da entrega
├── docs/
│   ├── documentacao.pdf          # documentação técnica (PPT/PDF)
│   ├── gerar_documentacao.py     # script ReportLab para gerar o PDF
│   └── prints/                   # evidências visuais
├── mvnw, mvnw.cmd, .mvn/         # Maven Wrapper
├── pom.xml
└── HELP.md
```
