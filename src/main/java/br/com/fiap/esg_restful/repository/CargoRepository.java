package br.com.fiap.esg_restful.repository;

import br.com.fiap.esg_restful.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
