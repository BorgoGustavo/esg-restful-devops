package br.com.fiap.esg_restful.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioLoginDTO(
        @NotBlank(message = "O e-mail do usuário é obrigatório.")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 20, message = "A senha deve conter entre 6 a 20 caracteres")
        String senha
) {
}