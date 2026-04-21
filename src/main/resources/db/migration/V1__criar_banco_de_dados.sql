-- Sequences
CREATE SEQUENCE seq_tbl_cargo START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_departamento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_colaborador START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_programa_treinamento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tbl_treinamento_colaborador START WITH 1 INCREMENT BY 1;

-- Tabela: tbl_cargo
CREATE TABLE tbl_cargo (
    id_cargo NUMBER(5) DEFAULT seq_tbl_cargo.NEXTVAL PRIMARY KEY,
    nome_cargo VARCHAR2(50) NOT NULL,
    nivel_cargo VARCHAR2(20) NOT NULL
);

-- Tabela: tbl_departamento
CREATE TABLE tbl_departamento (
    id_departamento NUMBER(5) DEFAULT seq_tbl_departamento.NEXTVAL PRIMARY KEY,
    nome_departamento VARCHAR2(20) NOT NULL
);

-- Tabela: tbl_colaborador
CREATE TABLE tbl_colaborador (
    id_colaborador NUMBER(5) DEFAULT seq_tbl_colaborador.NEXTVAL PRIMARY KEY,
    nome_colaborador VARCHAR2(50) NOT NULL,
    raca_etinia_colaborador VARCHAR2(20),
    genero_colaborador VARCHAR2(10),
    data_nascimento_colaborador DATE,
    data_admissao_colaborador DATE,
    id_departamento NUMBER(5),
    id_cargo NUMBER(5),
    salario_colaborador NUMBER(10,2),
    possui_deficiencia NUMBER(1),
    orientacao_sexual_colaborador VARCHAR2(20),
    CONSTRAINT fk_dept_colaborador FOREIGN KEY (id_departamento)
        REFERENCES tbl_departamento (id_departamento),
    CONSTRAINT fk_cargo_colaborador FOREIGN KEY (id_cargo)
        REFERENCES tbl_cargo (id_cargo)
);

-- Tabela: tbl_indicadores_departamento
CREATE TABLE tbl_indicadores_departamento (
    id_departamento NUMBER(5) PRIMARY KEY,
    quantidade_colaboradores NUMBER(6),
    percentual_mulheres NUMBER(5,2),
    percentual_negros NUMBER(5,2),
    percentual_pcds NUMBER(5,2),
    percentual_lgbtqia NUMBER(5,2),
    CONSTRAINT fk_indicador_departamento FOREIGN KEY (id_departamento)
        REFERENCES tbl_departamento (id_departamento)
);

-- Tabela: tbl_programa_treinamento
CREATE TABLE tbl_programa_treinamento (
    id_programa_treinamento NUMBER(5) DEFAULT seq_tbl_programa_treinamento.NEXTVAL PRIMARY KEY,
    nome_programa VARCHAR2(50),
    tipo_programa VARCHAR2(50),
    id_departamento NUMBER(5),
    status_programa CHAR(1),
    CONSTRAINT fk_programa_departamento FOREIGN KEY (id_departamento)
        REFERENCES tbl_departamento (id_departamento)
);

-- Tabela: tbl_treinamento_colaborador
CREATE TABLE tbl_treinamento_colaborador (
    id_treinamento NUMBER(5) DEFAULT seq_tbl_treinamento_colaborador.NEXTVAL PRIMARY KEY,
    id_colaborador NUMBER(5),
    id_programa_treinamento NUMBER(5),
    data_inicio_treinamento DATE,
    data_termino_treinamento DATE,
    CONSTRAINT fk_treinamento_colaborador FOREIGN KEY (id_colaborador)
        REFERENCES tbl_colaborador (id_colaborador) ON DELETE CASCADE,
    CONSTRAINT fk_programa_treinamento_colaborador FOREIGN KEY (id_programa_treinamento)
        REFERENCES tbl_programa_treinamento (id_programa_treinamento) ON DELETE CASCADE
);
