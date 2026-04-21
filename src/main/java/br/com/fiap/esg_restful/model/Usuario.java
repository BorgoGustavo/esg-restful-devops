package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_tbl_usuarios"
    )
    @SequenceGenerator(
            name = "seq_tbl_usuarios",
            sequenceName = "seq_tbl_usuarios",
            allocationSize = 1
    )
    @Column(name = "usuario_id")
    private Long userId;
    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(userId, usuario.userId) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && role == usuario.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nome, email, senha, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioRole.ADMIN){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else if (this.role == UsuarioRole.USER){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
