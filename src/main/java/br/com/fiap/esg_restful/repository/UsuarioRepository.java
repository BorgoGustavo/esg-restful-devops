package br.com.fiap.esg_restful.repository;

import br.com.fiap.esg_restful.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public UserDetails findByEmail(String email);
}
