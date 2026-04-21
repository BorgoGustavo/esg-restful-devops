CREATE OR REPLACE PROCEDURE prc_atualiza_indicadores_departamento AS
BEGIN
    DELETE FROM tbl_indicadores_departamento;
    FOR dept IN (
        SELECT id_departamento
            FROM tbl_departamento
    ) LOOP
        DECLARE
            v_total NUMBER := 0;
            v_mulheres NUMBER := 0;
            v_negros NUMBER := 0;
            v_pcds NUMBER := 0;
            v_lgbtqia NUMBER := 0;
        BEGIN
            SELECT COUNT(*)
            INTO v_total
                FROM tbl_colaborador
                WHERE id_departamento = dept.id_departamento;

            IF v_total > 0 THEN
                SELECT
                    COUNT(CASE WHEN genero_colaborador = 'Feminino' THEN 1 END),
                    COUNT(CASE WHEN raca_etinia_colaborador = 'Negro' THEN 1 END),
                    COUNT(CASE WHEN possui_deficiencia = 1 THEN 1 END),
                    COUNT(CASE WHEN orientacao_sexual_colaborador IS NOT NULL AND LOWER(orientacao_sexual_colaborador) <> 'heterossexual' THEN 1 END)
                INTO v_mulheres, v_negros, v_pcds, v_lgbtqia
                    FROM tbl_colaborador
                    WHERE id_departamento = dept.id_departamento;


                INSERT INTO tbl_indicadores_departamento (id_departamento, quantidade_colaboradores, percentual_mulheres, percentual_negros, percentual_pcds, percentual_lgbtqia)
                    VALUES (dept.id_departamento,
                    v_total,
                    ROUND((v_mulheres / v_total) * 100, 2),
                    ROUND((v_negros / v_total) * 100, 2),
                    ROUND((v_pcds / v_total) * 100, 2),
                    ROUND((v_lgbtqia / v_total) * 100, 2)
                );
            END IF;
        END;
    END LOOP;
END;
/

CREATE OR REPLACE TRIGGER trg_criar_treinamento_colaborador
AFTER INSERT ON tbl_colaborador
FOR EACH ROW
DECLARE
    v_id_departamento tbl_departamento.id_departamento%TYPE := :NEW.id_departamento;
    v_id_colaborador tbl_colaborador.id_colaborador%TYPE := :NEW.id_colaborador;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Trigger acionado');

    FOR treinamento IN (
        SELECT id_programa_treinamento
            FROM tbl_programa_treinamento
            WHERE id_departamento = v_id_departamento AND status_programa = 'A'
    )
        LOOP
            INSERT INTO tbl_treinamento_colaborador (id_colaborador, id_programa_treinamento, data_inicio_treinamento, data_termino_treinamento)
                VALUES (v_id_colaborador, treinamento.id_programa_treinamento, SYSDATE, NULL);
        END LOOP;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        NULL;
END;
/