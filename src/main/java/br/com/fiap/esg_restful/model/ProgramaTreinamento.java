package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_programa_treinamento")
public class ProgramaTreinamento {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_tbl_programa_treinamento"
    )
    @SequenceGenerator(
            name = "seq_tbl_programa_treinamento",
            sequenceName = "seq_tbl_programa_treinamento",
            allocationSize = 1
    )
    @Column(name = "id_programa_treinamento")
    private Long id;

    @Column(name = "nome_programa")
    private String nome;

    @Column(name = "tipo_programa")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @Column(name = "status_programa")
    private char status;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramaTreinamento that = (ProgramaTreinamento) o;
        return status == that.status && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(tipo, that.tipo) && Objects.equals(departamento, that.departamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, departamento, status);
    }
}
