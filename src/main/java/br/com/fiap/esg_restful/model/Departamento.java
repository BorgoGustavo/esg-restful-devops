package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_departamento")
public class Departamento {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_tbl_departamento"
    )
    @SequenceGenerator(
            name = "seq_tbl_departamento",
            sequenceName = "seq_tbl_departamento",
            allocationSize = 1
    )
    @Column(name = "id_departamento")
    private Long id;

    @Column(name = "nome_departamento")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
