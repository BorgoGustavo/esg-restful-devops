CREATE SEQUENCE seq_tbl_usuarios START WITH 1 INCREMENT BY 1;

CREATE TABLE tbl_usuarios (
    usuario_id INTEGER NOT NULL PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    senha      VARCHAR(20) NOT NULL,
    role       VARCHAR(50) DEFAULT 'USER'
);
