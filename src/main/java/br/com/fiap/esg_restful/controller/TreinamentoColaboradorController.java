package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.model.TreinamentoColaborador;
import br.com.fiap.esg_restful.service.TreinamentoColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TreinamentoColaboradorController {
    @Autowired
    private TreinamentoColaboradorService treinamentoColaboradorService;

    @GetMapping("/treinamentos-colaborador")
    public ResponseEntity listarTreinamentoColaborador(Pageable paginacao) {
        Page<TreinamentoColaborador> treinamentosColaborador = treinamentoColaboradorService.listarTreinamentoColaborador(paginacao);
        return ResponseEntity.ok().body(treinamentosColaborador);
    }
}
