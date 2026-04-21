package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tbl_colaborador")
public class Colaborador {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_tbl_colaborador"
    )
    @SequenceGenerator(
            name = "seq_tbl_colaborador",
            sequenceName = "seq_tbl_colaborador",
            allocationSize = 1
    )
    @Column(name = "id_colaborador")
    private Long id;

    @Column(name = "nome_colaborador")
    private String nome;

    @Column(name = "raca_etinia_colaborador")
    private String raca;

    @Column(name = "genero_colaborador")
    private String genero;

    @Column(name = "data_nascimento_colaborador")
    private LocalDate dataNascimento;

    @Column(name = "data_admissao_colaborador")
    private LocalDate dataAdmissao;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @Column(name = "salario_colaborador")
    private double salario;

    @Column(name = "possui_deficiencia")
    private boolean possuiDeficiencia;

    @Column(name = "orientacao_sexual_colaborador")
    private String orientacaoSexual;

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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isPossuiDeficiencia() {
        return possuiDeficiencia;
    }

    public void setPossuiDeficiencia(boolean possuiDeficiencia) {
        this.possuiDeficiencia = possuiDeficiencia;
    }

    public String getOrientacaoSexual() {
        return orientacaoSexual;
    }

    public void setOrientacaoSexual(String orientacaoSexual) {
        this.orientacaoSexual = orientacaoSexual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colaborador that = (Colaborador) o;
        return Double.compare(salario, that.salario) == 0 && possuiDeficiencia == that.possuiDeficiencia && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(raca, that.raca) && Objects.equals(genero, that.genero) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(dataAdmissao, that.dataAdmissao) && Objects.equals(departamento, that.departamento) && Objects.equals(cargo, that.cargo) && Objects.equals(orientacaoSexual, that.orientacaoSexual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, raca, genero, dataNascimento, dataAdmissao, departamento, cargo, salario, possuiDeficiencia, orientacaoSexual);
    }
}
