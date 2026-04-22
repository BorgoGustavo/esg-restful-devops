# Guia de coleta de prints

Todos os prints saem diretamente do GitHub (sem precisar de Docker local).
Salve cada um em `docs/prints/` com o nome exato entre parênteses.

**Run de referência:**
https://github.com/BorgoGustavo/esg-restful-devops/actions/runs/24753558494

---

## 1. `01-ci-green.png` — CI verde

URL: https://github.com/BorgoGustavo/esg-restful-devops/actions/workflows/ci.yml

Clique no último run verde. Tire print mostrando os jobs **`Build + Testes (JUnit)`** e
**`Empacotar JAR`** concluídos com ✓.

---

## 2. `02-cd-green.png` — CD completo (docker + staging + prod)

URL: https://github.com/BorgoGustavo/esg-restful-devops/actions/runs/24753558494

Tire print da visão geral mostrando os 3 jobs verdes:
- `Build & Push imagem Docker` ✓
- `Deploy STAGING` ✓
- `Deploy PRODUCAO` ✓

---

## 2b. `02b-cd-gate.png` — Gate de aprovação manual

Na mesma página do run, role até a seção **"Review deployments"**
(ou abra a aba Timeline e procure pelo evento de aprovação).
Alternativa: `Environments → production → Deployment history` da run —
mostra o "Approved by BorgoGustavo".

---

## 3. `03-ghcr.png` — Imagem no GHCR

URL: https://github.com/users/BorgoGustavo/packages/container/package/esg-restful-devops

Print mostrando a imagem com as tags `latest` e `sha-xxxxxxx`.

---

## 4. `04-staging-logs.png` — Logs do Deploy STAGING

URL: https://github.com/BorgoGustavo/esg-restful-devops/actions/runs/24753558494/job/72421898967

Expanda o step **"Subir stack STAGING"** e o step **"Aguardar healthcheck"**.
Tire print mostrando:
- `Container esg-db-staging  Healthy`
- `Container esg-app-staging  Started`
- `STAGING UP`

---

## 5. `05-prod-logs.png` — Logs do Deploy PRODUCAO

URL: https://github.com/BorgoGustavo/esg-restful-devops/actions/runs/24753558494/job/72421962722

Mesmo padrão do staging, mostrando:
- `Container esg-db-prod  Healthy`
- `Container esg-app-prod  Started`
- `PROD UP`

---

## 6. `06-health.png` — Smoke test

Ainda dentro do job `Deploy STAGING` (ou PROD), expanda:
- **"Smoke test - endpoint publico"** → `{"status":"UP","groups":["liveness","readiness"]}`
- **"Smoke test - endpoint protegido deve exigir auth"** → `HTTP 403`

Print mostrando as duas saídas.

---

## 7. `07-environments.png` — Environments

URL: https://github.com/BorgoGustavo/esg-restful-devops/settings/environments

Print mostrando os dois environments:
- `staging` (sem proteção)
- `production` (com "Required reviewers: BorgoGustavo")
