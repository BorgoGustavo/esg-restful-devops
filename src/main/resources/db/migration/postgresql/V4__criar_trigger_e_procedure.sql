-- Procedure: recalcula os indicadores de diversidade por departamento
CREATE OR REPLACE PROCEDURE prc_atualiza_indicadores_departamento()
LANGUAGE plpgsql
AS $$
DECLARE
    dept RECORD;
    v_total    INTEGER;
    v_mulheres INTEGER;
    v_negros   INTEGER;
    v_pcds     INTEGER;
    v_lgbtqia  INTEGER;
BEGIN
    DELETE FROM tbl_indicadores_departamento;

    FOR dept IN SELECT id_departamento FROM tbl_departamento LOOP
        SELECT COUNT(*) INTO v_total
          FROM tbl_colaborador
         WHERE id_departamento = dept.id_departamento;

        IF v_total > 0 THEN
            SELECT
                COUNT(*) FILTER (WHERE genero_colaborador = 'Feminino'),
                COUNT(*) FILTER (WHERE raca_etinia_colaborador = 'Negro'),
                COUNT(*) FILTER (WHERE possui_deficiencia = TRUE),
                COUNT(*) FILTER (WHERE orientacao_sexual_colaborador IS NOT NULL
                                   AND LOWER(orientacao_sexual_colaborador) <> 'heterossexual')
              INTO v_mulheres, v_negros, v_pcds, v_lgbtqia
              FROM tbl_colaborador
             WHERE id_departamento = dept.id_departamento;

            INSERT INTO tbl_indicadores_departamento (
                id_departamento, quantidade_colaboradores,
                percentual_mulheres, percentual_negros,
                percentual_pcds, percentual_lgbtqia
            ) VALUES (
                dept.id_departamento,
                v_total,
                ROUND((v_mulheres::NUMERIC / v_total) * 100, 2),
                ROUND((v_negros::NUMERIC   / v_total) * 100, 2),
                ROUND((v_pcds::NUMERIC     / v_total) * 100, 2),
                ROUND((v_lgbtqia::NUMERIC  / v_total) * 100, 2)
            );
        END IF;
    END LOOP;
END;
$$;

-- Trigger: ao inserir colaborador, matricula-o automaticamente nos treinamentos
-- ativos do seu departamento
CREATE OR REPLACE FUNCTION fn_criar_treinamento_colaborador()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    treinamento RECORD;
BEGIN
    FOR treinamento IN
        SELECT id_programa_treinamento
          FROM tbl_programa_treinamento
         WHERE id_departamento = NEW.id_departamento
           AND status_programa = 'A'
    LOOP
        INSERT INTO tbl_treinamento_colaborador (
            id_treinamento,
            id_colaborador,
            id_programa_treinamento,
            data_inicio_treinamento,
            data_termino_treinamento
        ) VALUES (
            nextval('seq_tbl_treinamento_colaborador'),
            NEW.id_colaborador,
            treinamento.id_programa_treinamento,
            CURRENT_DATE,
            NULL
        );
    END LOOP;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_criar_treinamento_colaborador
AFTER INSERT ON tbl_colaborador
FOR EACH ROW
EXECUTE FUNCTION fn_criar_treinamento_colaborador();
