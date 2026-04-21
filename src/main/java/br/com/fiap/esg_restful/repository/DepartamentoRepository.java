package br.com.fiap.esg_restful.repository;

import br.com.fiap.esg_restful.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
