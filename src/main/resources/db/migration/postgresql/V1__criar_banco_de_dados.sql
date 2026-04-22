-- Sequences (equivalentes as sequences Oracle originais)
CREATE SEQUENCE seq_tbl_cargo START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_departamento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_colaborador START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_programa_treinamento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_treinamento_colaborador START WITH 1 INCREMENT BY 1;

-- Tabela: tbl_cargo
CREATE TABLE tbl_cargo (
    id_cargo      BIGINT PRIMARY KEY,
    nome_cargo    VARCHAR(50) NOT NULL,
    nivel_cargo   VARCHAR(20) NOT NULL
);

-- Tabela: tbl_departamento
CREATE TABLE tbl_departamento (
    id_departamento   BIGINT PRIMARY KEY,
    nome_departamento VARCHAR(20) NOT NULL
);

-- Tabela: tbl_colaborador
CREATE TABLE tbl_colaborador (
    id_colaborador                BIGINT PRIMARY KEY,
    nome_colaborador              VARCHAR(50) NOT NULL,
    raca_etinia_colaborador       VARCHAR(20),
    genero_colaborador            VARCHAR(10),
    data_nascimento_colaborador   DATE,
    data_admissao_colaborador     DATE,
    id_departamento               BIGINT,
    id_cargo                      BIGINT,
    salario_colaborador           DOUBLE PRECISION,
    possui_deficiencia            BOOLEAN,
    orientacao_sexual_colaborador VARCHAR(20),
    CONSTRAINT fk_dept_colaborador  FOREIGN KEY (id_departamento) REFERENCES tbl_departamento (id_departamento),
    CONSTRAINT fk_cargo_colaborador FOREIGN KEY (id_cargo)        REFERENCES tbl_cargo (id_cargo)
);

-- Tabela: tbl_indicadores_departamento
CREATE TABLE tbl_indicadores_departamento (
    id_departamento          BIGINT PRIMARY KEY,
    quantidade_colaboradores INTEGER,
    percentual_mulheres      DOUBLE PRECISION,
    percentual_negros        DOUBLE PRECISION,
    percentual_pcds          DOUBLE PRECISION,
    percentual_lgbtqia       DOUBLE PRECISION,
    CONSTRAINT fk_indicador_departamento FOREIGN KEY (id_departamento)
        REFERENCES tbl_departamento (id_departamento)
);

-- Tabela: tbl_programa_treinamento
CREATE TABLE tbl_programa_treinamento (
    id_programa_treinamento BIGINT PRIMARY KEY,
    nome_programa           VARCHAR(50),
    tipo_programa           VARCHAR(50),
    id_departamento         BIGINT,
    status_programa         CHAR(1),
    CONSTRAINT fk_programa_departamento FOREIGN KEY (id_departamento)
        REFERENCES tbl_departamento (id_departamento)
);

-- Tabela: tbl_treinamento_colaborador
CREATE TABLE tbl_treinamento_colaborador (
    id_treinamento           BIGINT PRIMARY KEY,
    id_colaborador           BIGINT,
    id_programa_treinamento  BIGINT,
    data_inicio_treinamento  DATE,
    data_termino_treinamento DATE,
    CONSTRAINT fk_treinamento_colaborador
        FOREIGN KEY (id_colaborador)
        REFERENCES tbl_colaborador (id_colaborador) ON DELETE CASCADE,
    CONSTRAINT fk_programa_treinamento_colaborador
        FOREIGN KEY (id_programa_treinamento)
        REFERENCES tbl_programa_treinamento (id_programa_treinamento) ON DELETE CASCADE
);
