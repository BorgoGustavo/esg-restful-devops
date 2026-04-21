package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.model.Departamento;
import br.com.fiap.esg_restful.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/departamentos")
    public ResponseEntity listarDepartamentos(Pageable paginacao) {
        Page<Departamento> departamentos = departamentoService.listarDepartamentos(paginacao);
        return ResponseEntity.ok().body(departamentos);
    }

    @GetMapping("/departamentos/{id}")
    public ResponseEntity buscarDepartamentoPorId(@PathVariable Long id) {
        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);
        return ResponseEntity.ok().body(departamento);
    }

    @PostMapping("/departamentos")
    public ResponseEntity adicionarDepartamento(@RequestBody Departamento departamento) {
        Departamento departamentoSalvo = departamentoService.adicionarDepartamento(departamento);
        return ResponseEntity.ok().body(departamentoSalvo);
    }

    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity deletarDepartamento(@PathVariable Long id) {
        departamentoService.removerDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/departamentos")
    public ResponseEntity atualizarDepartamento(@RequestBody Departamento departamento) {
        Departamento departamentoSalvo = departamentoService.atualizarDepartamento(departamento);
        return ResponseEntity.ok().body(departamentoSalvo);
    }
}
