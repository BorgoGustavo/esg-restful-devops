package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.dto.UsuarioCadastroDto;
import br.com.fiap.esg_restful.dto.UsuarioExibicaoDto;
import br.com.fiap.esg_restful.model.Usuario;
import br.com.fiap.esg_restful.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDto gravarUsuario(UsuarioCadastroDto usuarioCadastroDto) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioCadastroDto.senha());
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDto, usuario);
        usuario.setSenha(senhaCriptografada);
        return new UsuarioExibicaoDto(usuarioRepository.save(usuario));
    }
}
