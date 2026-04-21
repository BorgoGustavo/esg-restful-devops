package br.com.fiap.esg_restful.controller;

import br.com.fiap.esg_restful.model.Cargo;
import br.com.fiap.esg_restful.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @GetMapping("/cargos")
    public ResponseEntity listarCargos(Pageable paginacao) {
        Page<Cargo> cargos = cargoService.listarCargos(paginacao);
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/cargos/{id}")
    public ResponseEntity buscarCargoPorId(@PathVariable Long id) {
        Cargo cargo = cargoService.buscarPorId(id);
        return ResponseEntity.ok().body(cargo);
    }

    @PostMapping("/cargos")
    public ResponseEntity adicionarCargo(@RequestBody Cargo cargo) {
        Cargo cargoSalvo = cargoService.adicionarCargo(cargo);
        return ResponseEntity.ok().body(cargoSalvo);
    }

    @DeleteMapping("/cargos/{id}")
    public ResponseEntity deletarCargo(@PathVariable Long id) {
        cargoService.removerCargo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("cargos")
    public ResponseEntity atualizarCargo(@RequestBody Cargo cargo) {
        Cargo cargoSalvo = cargoService.atualizarCargo(cargo);
        return ResponseEntity.ok().body(cargoSalvo);
    }

}
