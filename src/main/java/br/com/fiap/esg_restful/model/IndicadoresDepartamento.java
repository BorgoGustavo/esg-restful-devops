package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_indicadores_departamento")
public class IndicadoresDepartamento {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
    
    @Column(name = "quantidade_colaboradores")
    private int quantidadeColaboradores;
    
    @Column(name = "percentual_mulheres")
    private double percentualMulheres;
    
    @Column(name = "percentual_negros")
    private double percentualNegros;
    
    @Column(name = "percentual_pcds")
    private double percentualPcds;
    
    @Column(name = "percentual_lgbtqia")
    private double percentualLgbtqia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public int getQuantidadeColaboradores() {
        return quantidadeColaboradores;
    }

    public void setQuantidadeColaboradores(int quantidadeColaboradores) {
        this.quantidadeColaboradores = quantidadeColaboradores;
    }

    public double getPercentualMulheres() {
        return percentualMulheres;
    }

    public void setPercentualMulheres(double percentualMulheres) {
        this.percentualMulheres = percentualMulheres;
    }

    public double getPercentualNegros() {
        return percentualNegros;
    }

    public void setPercentualNegros(double percentualNegros) {
        this.percentualNegros = percentualNegros;
    }

    public double getPercentualPcds() {
        return percentualPcds;
    }

    public void setPercentualPcds(double percentualPcds) {
        this.percentualPcds = percentualPcds;
    }

    public double getPercentualLgbtqia() {
        return percentualLgbtqia;
    }

    public void setPercentualLgbtqia(double percentualLgbtqia) {
        this.percentualLgbtqia = percentualLgbtqia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndicadoresDepartamento that = (IndicadoresDepartamento) o;
        return quantidadeColaboradores == that.quantidadeColaboradores && Double.compare(percentualMulheres, that.percentualMulheres) == 0 && Double.compare(percentualNegros, that.percentualNegros) == 0 && Double.compare(percentualPcds, that.percentualPcds) == 0 && Double.compare(percentualLgbtqia, that.percentualLgbtqia) == 0 && Objects.equals(id, that.id) && Objects.equals(departamento, that.departamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departamento, quantidadeColaboradores, percentualMulheres, percentualNegros, percentualPcds, percentualLgbtqia);
    }
}
