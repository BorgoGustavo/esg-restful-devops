# Checklist de Entrega — Desafio DevOps (Cidades ESG Inteligentes)

**Integrante:** Gustavo Guimarães Borgo — RM560492
**Repositório:** https://github.com/BorgoGustavo/esg-restful-devops


| # | Item | Status | Onde encontrar |
|---|------|--------|----------------|
| 1 | Projeto compactado em `.ZIP` com estrutura organizada | ✅ | `esg-restful-devops.zip` (raiz da entrega) |
| 2 | Dockerfile funcional | ✅ | `./Dockerfile` (multi-stage, usuário não-root, healthcheck) |
| 3 | `docker-compose.yml` (ou arquivos Kubernetes) | ✅ | `./docker-compose.yml`, `./docker-compose.staging.yml`, `./docker-compose.prod.yml` |
| 4 | Pipeline com etapas de build, teste e deploy | ✅ | `./.github/workflows/ci.yml` (build + test) e `./.github/workflows/cd.yml` (docker + deploy staging + deploy prod) |
| 5 | `README.md` com instruções e prints | ✅ | `./README.md` + `./docs/prints/` |
| 6 | Documentação técnica com evidências (PDF ou PPT) | ✅ | `./docs/documentacao.pdf` |
| 7 | Deploy realizado nos ambientes staging e produção | ✅ | Prints `04-staging-ps.png` e `05-prod-ps.png` + aba _Environments_ do GitHub (`07-environments.png`) |

## Entregáveis do repositório

- [x] **Código-fonte completo** — `src/main/java` + `src/main/resources` + `src/test`
- [x] **Arquivos de CI/CD** — `.github/workflows/ci.yml` e `.github/workflows/cd.yml`
- [x] **Dockerfile** — multi-stage (Maven build → Temurin JRE runtime)
- [x] **Orquestrador** — 3 `docker-compose*.yml` (default, staging, prod)
- [x] **Scripts de ambiente** — `mvnw`, `mvnw.cmd`, `.mvn/`
- [x] **`.env.example`** — todas as variáveis de ambiente documentadas
- [x] **Logs/prints** — `docs/prints/` (evidências visuais do pipeline e dos ambientes)
- [x] **Testes automatizados** — 3 testes JUnit (`contextLoads`, `actuatorHealthDeveResponderUp`, `endpointProtegidoDeveRetornar403QuandoSemToken`)

## Seções exigidas no README

- [x] `# Projeto - Cidades ESG Inteligentes`
- [x] `## Como executar localmente com Docker`
- [x] `## Pipeline CI/CD`
- [x] `## Containerização`
- [x] `## Prints do funcionamento`
- [x] `## Tecnologias utilizadas`

## Seções exigidas na documentação (PDF)

- [x] Título do projeto + nome dos integrantes
- [x] Descrição do pipeline: ferramenta, etapas e lógica
- [x] Docker: arquitetura, comandos usados, imagem criada
- [x] Prints do pipeline rodando (build, testes, deploy)
- [x] Prints dos ambientes staging e produção funcionando
- [x] Desafios encontrados e como foram resolvidos
