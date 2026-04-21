package br.com.fiap.esg_restful.repository;

import br.com.fiap.esg_restful.model.IndicadoresDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface IndicadoresDepartamentoRepository extends JpaRepository<IndicadoresDepartamento, Long> {

    @Procedure(procedureName = "PRC_ATUALIZA_INDICADORES_DEPARTAMENTO")
    void atualizarIndicadoresDepartamento();
}
