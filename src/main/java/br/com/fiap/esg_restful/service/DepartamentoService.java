package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.exception.EntidadeNaoEncontradaException;
import br.com.fiap.esg_restful.model.Departamento;
import br.com.fiap.esg_restful.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Page<Departamento> listarDepartamentos(Pageable paginacao) {
        return departamentoRepository.findAll(paginacao);
    }

    public Departamento buscarDepartamentoPorId(Long id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        if (departamento.isPresent()) {
            return departamento.get();
        }else {
            throw new EntidadeNaoEncontradaException("Departamento com id: " + id + " não encontrado");
        }
    }

    public Departamento adicionarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public void removerDepartamento(Long id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        if (departamento.isPresent()) {
            departamentoRepository.deleteById(id);
        }
        else {
            throw new EntidadeNaoEncontradaException("Departamento com id: " + id + " não encontrado");
        }
    }

    public Departamento atualizarDepartamento(Departamento departamento) {
        Optional<Departamento> optionalDepartamento = departamentoRepository.findById(departamento.getId());
        if (optionalDepartamento.isPresent()) {
            return departamentoRepository.save(departamento);
        }else {
            throw new EntidadeNaoEncontradaException("Departamento com id:" + departamento.getId() + "não encontrado");
        }
    }

}
