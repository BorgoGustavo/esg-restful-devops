package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.exception.EntidadeNaoEncontradaException;
import br.com.fiap.esg_restful.model.Cargo;
import br.com.fiap.esg_restful.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    public Page<Cargo> listarCargos(Pageable paginacao) {
        return cargoRepository.findAll(paginacao);
    }

    public Cargo buscarPorId(Long id) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);
        if (cargoOptional.isPresent()) {
            return cargoOptional.get();
        }else {
            throw new EntidadeNaoEncontradaException("Cargo com ID: " + id + " nao encontrado");
        }
    }

    public Cargo adicionarCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public void removerCargo(Long id) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);
        if (cargoOptional.isPresent()) {
            cargoRepository.deleteById(id);
        }else {
            throw new EntidadeNaoEncontradaException("Cargo com ID: " + id + " nao encontrado");
        }

    }

    public Cargo atualizarCargo(Cargo cargo) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(cargo.getId());
        if (cargoOptional.isPresent()) {
            return cargoRepository.save(cargo);
        }else {
            throw new EntidadeNaoEncontradaException("Cargo com ID: " + cargo.getId() + " nao encontrado");
        }

    }
}
