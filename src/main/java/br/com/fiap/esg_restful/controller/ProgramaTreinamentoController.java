package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.model.ProgramaTreinamento;
import br.com.fiap.esg_restful.service.ProgramaTreinamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProgramaTreinamentoController {
    @Autowired
    private ProgramaTreinamentoService programaTreinamentoService;

    @GetMapping("/programas-treinamento")
    public ResponseEntity listarProgramasTreinamento(Pageable paginacao) {
        Page<ProgramaTreinamento> treinamentos = programaTreinamentoService.listarProgramasTreinamento(paginacao);
        return ResponseEntity.ok(treinamentos.getContent());
    }

    @GetMapping("/programas-treinamento/{id}")
    public ResponseEntity buscarProgramaTreinamentoPorId(@PathVariable Long id) {
        ProgramaTreinamento programaTreinamento = programaTreinamentoService.buscarProgramaTreinamentoPorId(id);
        return ResponseEntity.ok(programaTreinamento);
    }

    @PostMapping("/programas-treinamento")
    public ResponseEntity adicionarProgramaTreinamento(@RequestBody ProgramaTreinamento programaTreinamento) {
        ProgramaTreinamento programaTreinamentoSalvo = programaTreinamentoService.adicionarProgramaTreinamento(programaTreinamento);
        return ResponseEntity.ok(programaTreinamentoSalvo);
    }

    @DeleteMapping("/programas-treinamento/{id}")
    public ResponseEntity deletarProgramaTreinamento(@PathVariable Long id) {
        programaTreinamentoService.removerProgramaTreinamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/programas-treinamento")
    public ResponseEntity atualizarProgramaTreinamento(@RequestBody ProgramaTreinamento programaTreinamento) {
        ProgramaTreinamento programaTreinamentoSalvo = programaTreinamentoService.atualizarProgramaTreinamento(programaTreinamento);
        return ResponseEntity.ok(programaTreinamentoSalvo);
    }
}
