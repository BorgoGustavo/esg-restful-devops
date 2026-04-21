package br.com.fiap.esg_restful.repository;

import br.com.fiap.esg_restful.model.ProgramaTreinamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramaTreinamentoRepository extends JpaRepository<ProgramaTreinamento, Long> {
}
