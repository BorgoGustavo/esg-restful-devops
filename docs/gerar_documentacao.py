#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Gera docs/documentacao.pdf — documentação técnica do desafio DevOps
(Cidades ESG Inteligentes). Usa fpdf2 (core fonts, sem dependência externa).

Uso:
    cd esg-restful
    pip install fpdf2
    python docs/gerar_documentacao.py
"""

from fpdf import FPDF
import os
import sys

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
PRINTS_DIR = os.path.join(BASE_DIR, "prints")
OUTPUT = os.path.join(BASE_DIR, "documentacao.pdf")

# -----------------------------------------------------------------------------
# Dados customizaveis pelo aluno (edite antes de gerar o PDF)
# -----------------------------------------------------------------------------
INTEGRANTES = [
    ("Gustavo Guimaraes Borgo", "RM560492"),
]
DISCIPLINA = "Navegando pelo mundo DevOps - FIAP"
PROJETO = "Cidades ESG Inteligentes"
REPO_URL = "https://github.com/BorgoGustavo/esg-restful-devops"


def _latin1(text: str) -> str:
    """Core fonts do fpdf2 so suportam latin-1; troca caracteres problematicos."""
    replacements = {
        "—": "-", "–": "-",
        "“": '"', "”": '"',
        "‘": "'", "’": "'",
        "…": "...", "·": "-",
        "→": "->", "←": "<-",
        "✓": "[OK]", "✗": "[X]",
        "°": "o",
    }
    for k, v in replacements.items():
        text = text.replace(k, v)
    return text


class PDF(FPDF):
    def header(self):
        if self.page_no() > 1:
            self.set_font("Helvetica", "I", 8)
            self.set_text_color(120, 120, 120)
            self.cell(0, 8, _latin1(f"{PROJETO} - {DISCIPLINA}"), align="C")
            self.ln(10)

    def footer(self):
        self.set_y(-15)
        self.set_font("Helvetica", "I", 8)
        self.set_text_color(120, 120, 120)
        self.cell(0, 10, f"Pagina {self.page_no()}/{{nb}}", align="C")

    # helpers -----------------------------------------------------------
    def titulo_secao(self, numero, titulo):
        if self.get_y() > 220:
            self.add_page()
        self.set_font("Helvetica", "B", 16)
        self.set_text_color(0, 70, 130)
        self.cell(0, 12, _latin1(f"{numero}. {titulo}"), new_x="LMARGIN", new_y="NEXT")
        self.set_draw_color(0, 70, 130)
        self.set_line_width(0.6)
        self.line(self.l_margin, self.get_y(), self.w - self.r_margin, self.get_y())
        self.ln(6)

    def sub(self, titulo):
        self.set_font("Helvetica", "B", 12)
        self.set_text_color(40, 40, 40)
        self.cell(0, 10, _latin1(titulo), new_x="LMARGIN", new_y="NEXT")
        self.ln(1)

    def corpo(self, texto):
        self.set_font("Helvetica", "", 10)
        self.set_text_color(30, 30, 30)
        self.multi_cell(0, 5.5, _latin1(texto), new_x="LMARGIN", new_y="NEXT")
        self.ln(3)

    def bullet(self, itens):
        self.set_font("Helvetica", "", 10)
        self.set_text_color(30, 30, 30)
        for it in itens:
            self.multi_cell(0, 5.5, _latin1(f"  - {it}"),
                            new_x="LMARGIN", new_y="NEXT")
        self.ln(2)

    def codigo(self, texto):
        self.set_font("Courier", "", 7.5)
        self.set_text_color(20, 20, 20)
        self.set_fill_color(240, 240, 240)
        w = self.w - self.l_margin - self.r_margin
        for linha in _latin1(texto).split("\n"):
            if self.get_y() + 4.2 > self.h - 20:
                self.add_page()
            # multi_cell com fill e quebra automatica se a linha for muito longa
            self.multi_cell(w, 4.2, linha if linha else " ", fill=True,
                            new_x="LMARGIN", new_y="NEXT")
        self.ln(4)

    def imagem_print(self, arquivo, legenda):
        caminho = os.path.join(PRINTS_DIR, arquivo)
        if not os.path.exists(caminho):
            self.set_font("Helvetica", "I", 9)
            self.set_text_color(180, 60, 60)
            self.cell(0, 6, _latin1(f"[print pendente: docs/prints/{arquivo}]"),
                     new_x="LMARGIN", new_y="NEXT")
            self.set_text_color(30, 30, 30)
            return
        max_w = self.w - self.l_margin - self.r_margin
        if self.get_y() + 90 > self.h - 25:
            self.add_page()
        self.image(caminho, w=max_w)
        self.ln(2)
        self.set_font("Helvetica", "I", 9)
        self.set_text_color(100, 100, 100)
        self.multi_cell(0, 5, _latin1(f"Figura: {legenda}"),
                        new_x="LMARGIN", new_y="NEXT")
        self.set_text_color(30, 30, 30)
        self.ln(3)


def gerar():
    pdf = PDF()
    pdf.alias_nb_pages()
    pdf.set_auto_page_break(auto=True, margin=18)

    # ------ Capa ---------------------------------------------------------
    pdf.add_page()
    pdf.set_font("Helvetica", "B", 22)
    pdf.set_text_color(0, 70, 130)
    pdf.ln(40)
    pdf.multi_cell(0, 14, _latin1("Desafio DevOps"), align="C",
                   new_x="LMARGIN", new_y="NEXT")
    pdf.set_font("Helvetica", "B", 18)
    pdf.multi_cell(0, 12, _latin1(PROJETO), align="C",
                   new_x="LMARGIN", new_y="NEXT")
    pdf.ln(10)
    pdf.set_font("Helvetica", "", 12)
    pdf.set_text_color(60, 60, 60)
    pdf.multi_cell(0, 8, _latin1(DISCIPLINA), align="C",
                   new_x="LMARGIN", new_y="NEXT")
    pdf.ln(18)
    pdf.set_font("Helvetica", "B", 12)
    pdf.set_text_color(40, 40, 40)
    pdf.cell(0, 8, _latin1("Integrantes"), align="C", new_x="LMARGIN", new_y="NEXT")
    pdf.set_font("Helvetica", "", 11)
    for nome, rm in INTEGRANTES:
        pdf.cell(0, 7, _latin1(f"{nome}  -  {rm}"), align="C", new_x="LMARGIN", new_y="NEXT")
    pdf.ln(15)
    pdf.set_font("Helvetica", "I", 10)
    pdf.set_text_color(100, 100, 100)
    pdf.cell(0, 6, _latin1(f"Repositorio: {REPO_URL}"), align="C", new_x="LMARGIN", new_y="NEXT")

    # ------ Secao 1: Contexto -------------------------------------------
    pdf.add_page()
    pdf.titulo_secao(1, "Contexto do Projeto")
    pdf.corpo(
        "Este trabalho e a entrega do desafio 'Navegando pelo mundo DevOps'. "
        "Partimos de um projeto Java/Spring Boot desenvolvido em fases anteriores "
        "com tema ESG (Cidades ESG Inteligentes) e o adaptamos para um cenario "
        "proximo de producao, aplicando tecnicas de containerizacao, orquestracao "
        "de infraestrutura e um pipeline automatizado de CI/CD."
    )
    pdf.sub("Dominio da aplicacao")
    pdf.corpo(
        "A API expoe recursos REST para gerir colaboradores, departamentos, cargos, "
        "programas de treinamento e indicadores de diversidade (% de mulheres, "
        "negros, PCDs e LGBTQIA+ por departamento) - o pilar Social do ESG. "
        "Autenticacao e feita via JWT (Spring Security + auth0/java-jwt)."
    )
    pdf.sub("Stack tecnologica")
    pdf.bullet([
        "Java 21 (Eclipse Temurin)",
        "Spring Boot 3.4.5 (Web, Data JPA, Security, Validation, Actuator)",
        "PostgreSQL 16 (em Docker) / Oracle (legado FIAP) / H2 (testes)",
        "Flyway (migrations versionadas) com drivers Oracle e PostgreSQL",
        "Maven 3.9 (Maven Wrapper)",
        "Docker (multi-stage) + Docker Compose v2",
        "GitHub Actions + GitHub Container Registry (GHCR)",
        "JUnit 5, Spring Boot Test, MockMvc",
    ])

    # ------ Secao 2: Pipeline CI/CD -------------------------------------
    pdf.add_page()
    pdf.titulo_secao(2, "Pipeline CI/CD")
    pdf.corpo(
        "A ferramenta escolhida foi o GitHub Actions, pela integracao nativa com o "
        "repositorio, gratuidade para projetos publicos e suporte de primeira classe "
        "a Docker e ao GitHub Container Registry (GHCR). O pipeline foi dividido em "
        "dois workflows independentes: ci.yml (rapido, roda em todo push/PR) e cd.yml "
        "(publica imagem e realiza deploy em staging e producao)."
    )
    pdf.sub("ci.yml - Integracao Continua")
    pdf.bullet([
        "Trigger: push e pull_request em main/develop, ou manual (workflow_dispatch).",
        "Job 'build-test': checkout, setup JDK 21 Temurin com cache Maven, roda "
        "'./mvnw test -Dspring.profiles.active=test' (H2 em memoria) e publica os "
        "relatorios surefire como artifact.",
        "Job 'package': depende do teste verde; executa './mvnw -DskipTests package' "
        "e anexa o JAR final como artifact.",
    ])
    pdf.sub("cd.yml - Entrega Continua")
    pdf.bullet([
        "Trigger: push em main.",
        "Job 'docker': docker buildx + login no GHCR (usando GITHUB_TOKEN), gera tags "
        "'sha-<7>' e 'latest' via docker/metadata-action, e faz build/push com cache GHA.",
        "Job 'deploy-staging': environment 'staging' - sobe a stack docker-compose."
        "staging.yml dentro do runner, aguarda /actuator/health ficar UP e roda smoke "
        "tests (health 200 e endpoint protegido 401/403).",
        "Job 'deploy-prod': environment 'production' protegido por 'Required reviewers' "
        "- so executa apos aprovacao manual - re-executa o mesmo padrao na porta 8090.",
    ])
    pdf.sub("Fluxo visual")
    pdf.codigo(
        "push main --> CI: build-test --> package\n"
        "                               |\n"
        "                               v\n"
        "                   CD: docker (build + push GHCR)\n"
        "                               |\n"
        "                               v\n"
        "                       deploy-staging (auto)\n"
        "                               |\n"
        "                               v\n"
        "                       deploy-prod (manual gate)"
    )
    pdf.imagem_print("01-ci-green.png",
                     "Execucao verde do workflow CI (build-test + package).")
    pdf.imagem_print("02-cd-green.png",
                     "Execucao verde do workflow CD (docker + deploy staging + deploy prod).")
    pdf.imagem_print("07-environments.png",
                     "Aba Environments do GitHub: staging sem gate, production com aprovacao manual.")

    # ------ Secao 3: Docker ---------------------------------------------
    pdf.add_page()
    pdf.titulo_secao(3, "Docker - Arquitetura e Imagem")
    pdf.corpo(
        "A aplicacao foi containerizada com um Dockerfile multi-stage. O primeiro "
        "estagio (maven:3.9-eclipse-temurin-21) baixa dependencias e gera o JAR; o "
        "segundo (eclipse-temurin:21-jre-alpine) contem apenas o runtime, um usuario "
        "nao-root chamado 'spring' e o healthcheck nativo apontando para /actuator/health. "
        "A imagem final fica em torno de 200 MB."
    )
    pdf.sub("Principais comandos")
    pdf.codigo(
        "# Build da imagem\n"
        "docker build -t esg-restful:local .\n\n"
        "# Subir staging (app + postgres)\n"
        "cp .env.example .env\n"
        "docker compose -f docker-compose.staging.yml up -d --build\n\n"
        "# Subir producao em paralelo (portas 8090 / 5433)\n"
        "cp .env.example .env.prod\n"
        "docker compose -f docker-compose.prod.yml --env-file .env.prod up -d\n\n"
        "# Ver status\n"
        "docker compose -f docker-compose.staging.yml ps\n"
        "curl http://localhost:8080/actuator/health"
    )
    pdf.sub("Dockerfile (trecho principal)")
    pdf.codigo(
        "FROM maven:3.9-eclipse-temurin-21 AS build\n"
        "WORKDIR /build\n"
        "COPY pom.xml .mvn/ mvnw ./\n"
        "RUN ./mvnw -B -q -DskipTests dependency:go-offline\n"
        "COPY src ./src\n"
        "RUN ./mvnw -B -q -DskipTests package\n\n"
        "FROM eclipse-temurin:21-jre-alpine\n"
        "RUN apk add --no-cache curl && addgroup -S spring && adduser -S spring -G spring\n"
        "WORKDIR /app\n"
        "COPY --from=build /build/target/esg-restful.jar /app/app.jar\n"
        "USER spring\n"
        "EXPOSE 8080\n"
        "HEALTHCHECK CMD curl -fsS http://localhost:8080/actuator/health | grep -q 'UP'"
    )
    pdf.sub("Orquestracao (docker-compose)")
    pdf.bullet([
        "Dois arquivos (staging e prod) com 'name:' distinto, redes isoladas "
        "(esg-staging / esg-prod) e volumes nomeados (pgdata-staging / pgdata-prod).",
        "Variaveis sensiveis (JWT_SECRET, DB_PASSWORD) vem do arquivo .env.",
        "depends_on: service_healthy - app so sobe apos o Postgres responder pg_isready.",
        "Portas distintas no host (8080/5432 em staging, 8090/5433 em prod) para rodar "
        "os dois ambientes simultaneamente na mesma maquina.",
    ])
    pdf.imagem_print("03-ghcr.png",
                     "Imagem publicada no GitHub Container Registry com tag sha + latest.")
    pdf.imagem_print("04-staging-ps.png",
                     "Containers do ambiente STAGING em execucao (app + postgres).")
    pdf.imagem_print("05-prod-ps.png",
                     "Containers do ambiente PRODUCAO em execucao (portas 8090/5433).")
    pdf.imagem_print("06-health.png",
                     "Endpoint /actuator/health respondendo UP em ambos os ambientes.")

    # ------ Secao 4: Profiles & Migrations ------------------------------
    pdf.add_page()
    pdf.titulo_secao(4, "Profiles Spring e Migrations")
    pdf.corpo(
        "Para permitir que o mesmo codebase rode em tres contextos distintos "
        "(faculdade, pipeline, producao containerizada), adotamos profiles do Spring:"
    )
    pdf.codigo(
        "default -> Oracle FIAP     | db/migration/oracle     | IDE local\n"
        "docker  -> PostgreSQL 16   | db/migration/postgresql | staging / prod\n"
        "test    -> H2 in-memory    | (Flyway off, JPA create-drop) | CI/CD"
    )
    pdf.corpo(
        "As 4 migrations Oracle originais (sequences, triggers Oracle, PL/SQL) foram "
        "mantidas intactas em db/migration/oracle/ e reescritas em sintaxe PostgreSQL "
        "(SERIAL/NEXTVAL, COUNT FILTER, plpgsql) em db/migration/postgresql/. A "
        "selecao ocorre em tempo de bootstrap via a propriedade spring.flyway.locations."
    )

    # ------ Secao 5: Desafios -------------------------------------------
    pdf.add_page()
    pdf.titulo_secao(5, "Desafios encontrados e resolucoes")
    pdf.sub("5.1. Projeto original dependia do Oracle FIAP (fora da VPN nao funciona)")
    pdf.corpo(
        "Resolucao: externalizar as credenciais em variaveis de ambiente, criar o "
        "profile 'docker' com PostgreSQL, reescrever as migrations Oracle em sintaxe "
        "Postgres e separa-las em subpastas (oracle/ e postgresql/). O codigo Java "
        "nao precisou ser alterado (JPA abstrai o dialeto) - apenas application-docker.properties."
    )
    pdf.sub("5.2. Actuator e Security bloqueavam o healthcheck do Docker")
    pdf.corpo(
        "Por padrao, o Spring Security autenticava todas as rotas, inclusive "
        "/actuator/health. Resolucao: liberar explicitamente '/actuator/health/**' "
        "e '/actuator/info' via '.permitAll()' no SecurityConfig, deixando o resto "
        "protegido por JWT. Assim o healthcheck do container funciona sem token."
    )
    pdf.sub("5.3. Rodar staging e producao na mesma maquina sem colisao")
    pdf.corpo(
        "Resolucao: dois docker-compose com 'name:' distinto, networks e volumes "
        "separados e portas publicadas diferentes no host (8080/5432 vs 8090/5433). "
        "O atributo 'name:' evita que o Compose reuse o mesmo projeto logico."
    )
    pdf.sub("5.4. Deploy 'real' sem servidor proprio para o trabalho academico")
    pdf.corpo(
        "Resolucao: usar o proprio runner do GitHub Actions como ambiente de execucao. "
        "O job deploy-staging/deploy-prod faz 'docker compose up -d' no runner, aguarda "
        "o healthcheck e roda smoke tests (curl no /actuator/health e no endpoint "
        "protegido esperando 401/403). Isso gera evidencia automatica (logs + status "
        "verde do job) equivalente a um deploy em servidor, mantendo o custo zero."
    )
    pdf.sub("5.5. Garantir testes automatizados sem precisar de banco externo no CI")
    pdf.corpo(
        "Resolucao: profile 'test' com H2 em memoria (MODE=PostgreSQL) e Flyway "
        "desativado; Hibernate gera o schema via ddl-auto=create-drop. Adicionamos 3 "
        "testes (contextLoads, actuator health 200, endpoint protegido 403) que rodam "
        "em menos de 5s dentro do runner."
    )

    # ------ Secao 6: Checklist ------------------------------------------
    pdf.add_page()
    pdf.titulo_secao(6, "Checklist de entrega")
    itens = [
        "Projeto compactado em .ZIP com estrutura organizada",
        "Dockerfile funcional",
        "docker-compose.yml (staging, prod e default)",
        "Pipeline com etapas de build, teste e deploy",
        "README.md com instrucoes e prints",
        "Documentacao tecnica com evidencias (este PDF)",
        "Deploy realizado nos ambientes staging e producao",
    ]
    pdf.set_font("Helvetica", "", 11)
    for it in itens:
        pdf.cell(0, 7, _latin1(f"[X]  {it}"), new_x="LMARGIN", new_y="NEXT")

    pdf.output(OUTPUT)
    print(f"PDF gerado em: {OUTPUT}")


if __name__ == "__main__":
    try:
        gerar()
    except Exception as e:
        print(f"Erro ao gerar PDF: {e}", file=sys.stderr)
        sys.exit(1)
