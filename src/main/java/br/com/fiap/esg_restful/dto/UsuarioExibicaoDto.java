package br.com.fiap.esg_restful.dto;

import br.com.fiap.esg_restful.model.Usuario;
import br.com.fiap.esg_restful.model.UsuarioRole;

public record UsuarioExibicaoDto(
        Long usuarioId,
        String nome,
        String email,
        UsuarioRole role
) {
    public UsuarioExibicaoDto(Usuario usuario) {
        this(
                usuario.getUserId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}