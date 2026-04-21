package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.model.IndicadoresDepartamento;
import br.com.fiap.esg_restful.repository.IndicadoresDepartamentoRepository;
import br.com.fiap.esg_restful.service.IndicadoresDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndicadoresDepartamentoController {
    @Autowired
    private IndicadoresDepartamentoService indicadoresDepartamentoService;

    @GetMapping("/indicadores-departamento")
    public ResponseEntity listarIndicadores(Pageable paginacao) {
        Page<IndicadoresDepartamento> indicadoresDepartamentos = indicadoresDepartamentoService.listarIndicadores(paginacao);
        return ResponseEntity.ok().body(indicadoresDepartamentos);
    }
}
