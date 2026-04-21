package br.com.fiap.esg_restful.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tbl_treinamento_colaborador")
public class TreinamentoColaborador {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_tbl_treinamento_colaborador"
    )
    @SequenceGenerator(
            name = "seq_tbl_treinamento_colaborador",
            sequenceName = "seq_tbl_treinamento_colaborador",
            allocationSize = 1
    )
    @Column(name = "id_treinamento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_colaborador")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "id_programa_treinamento")
    private ProgramaTreinamento programaTreinamento;

    @Column(name = "data_inicio_treinamento")
    private LocalDate dataInicioTreinamento;

    @Column(name = "data_termino_treinamento")
    private LocalDate dataTerminoTreinamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public ProgramaTreinamento getProgramaTreinamento() {
        return programaTreinamento;
    }

    public void setProgramaTreinamento(ProgramaTreinamento programaTreinamento) {
        this.programaTreinamento = programaTreinamento;
    }

    public LocalDate getDataInicioTreinamento() {
        return dataInicioTreinamento;
    }

    public void setDataInicioTreinamento(LocalDate dataInicioTreinamento) {
        this.dataInicioTreinamento = dataInicioTreinamento;
    }

    public LocalDate getDataTerminoTreinamento() {
        return dataTerminoTreinamento;
    }

    public void setDataTerminoTreinamento(LocalDate dataTerminoTreinamento) {
        this.dataTerminoTreinamento = dataTerminoTreinamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreinamentoColaborador that = (TreinamentoColaborador) o;
        return Objects.equals(id, that.id) && Objects.equals(colaborador, that.colaborador) && Objects.equals(programaTreinamento, that.programaTreinamento) && Objects.equals(dataInicioTreinamento, that.dataInicioTreinamento) && Objects.equals(dataTerminoTreinamento, that.dataTerminoTreinamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, colaborador, programaTreinamento, dataInicioTreinamento, dataTerminoTreinamento);
    }
}
