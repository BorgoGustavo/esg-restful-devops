package br.com.fiap.esg_restful.model;

public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    private UsuarioRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
