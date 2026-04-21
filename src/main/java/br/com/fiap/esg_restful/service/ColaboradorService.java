package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.exception.EntidadeNaoEncontradaException;
import br.com.fiap.esg_restful.model.Colaborador;
import br.com.fiap.esg_restful.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Page<Colaborador> listarColaboradores(Pageable paginacao) {
        return colaboradorRepository.findAll(paginacao);
    }

    public Colaborador buscarColaboradorPorId(Long id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        if (colaborador.isPresent()) {
            return colaborador.get();
        }else{
            throw new EntidadeNaoEncontradaException("Colaborador de ID: " + id + " não encontrado");
        }
    }

    public Colaborador adicionarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public void removerColaborador(Long id) {
        Optional<Colaborador> colaboradorOptional = colaboradorRepository.findById(id);
        System.out.println(colaboradorOptional.isPresent());
        if (colaboradorOptional.isPresent()) {
            colaboradorRepository.deleteById(id);
        }else {
            throw new EntidadeNaoEncontradaException("Colaborador de ID: " + id + " não encontrado");
        }
    }

    public Colaborador atualizarColaborador(Colaborador colaborador) {
        Optional<Colaborador> colaboradorOptional = colaboradorRepository.findById(colaborador.getId());
        if (colaboradorOptional.isPresent()) {
            return colaboradorRepository.save(colaborador);
        }else {
            throw new EntidadeNaoEncontradaException("Colaborador de ID: " + colaborador.getId() + " não encontrado");
        }
    }
}
