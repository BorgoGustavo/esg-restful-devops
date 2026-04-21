package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.config.security.TokenService;
import br.com.fiap.esg_restful.dto.TokenDto;
import br.com.fiap.esg_restful.dto.UsuarioCadastroDto;
import br.com.fiap.esg_restful.dto.UsuarioExibicaoDto;
import br.com.fiap.esg_restful.dto.UsuarioLoginDTO;
import br.com.fiap.esg_restful.model.Usuario;
import br.com.fiap.esg_restful.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity realizarLogin(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(usuarioLoginDto.email(), usuarioLoginDto.senha());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDto(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrarUsuario(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
        UsuarioExibicaoDto usuarioExibicaoDto = usuarioService.gravarUsuario(usuarioCadastroDto);
        return ResponseEntity.ok(usuarioExibicaoDto);
    }
}
