package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.exception.EntidadeNaoEncontradaException;
import br.com.fiap.esg_restful.model.ProgramaTreinamento;
import br.com.fiap.esg_restful.repository.ProgramaTreinamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgramaTreinamentoService {
    @Autowired
    private ProgramaTreinamentoRepository programaTreinamentoRepository;

    public Page<ProgramaTreinamento> listarProgramasTreinamento(Pageable paginacao) {
        return programaTreinamentoRepository.findAll(paginacao);
    }

    public ProgramaTreinamento buscarProgramaTreinamentoPorId(Long id) {
        Optional<ProgramaTreinamento> programaTreinamentoOptional = programaTreinamentoRepository.findById(id);
        if (programaTreinamentoOptional.isPresent()) {
            return programaTreinamentoOptional.get();
        }else {
            throw new EntidadeNaoEncontradaException("Programa de Treinamento de ID: " + id + "não encontrado");
        }
    }

    public ProgramaTreinamento adicionarProgramaTreinamento(ProgramaTreinamento programaTreinamento) {
        return programaTreinamentoRepository.save(programaTreinamento);
    }

    public void removerProgramaTreinamento(Long id) {
        Optional<ProgramaTreinamento> programaTreinamentoOptional = programaTreinamentoRepository.findById(id);
        if (programaTreinamentoOptional.isPresent()) {
            programaTreinamentoRepository.delete(programaTreinamentoOptional.get());
        }else {
            throw new EntidadeNaoEncontradaException("Programa de Treinamento de ID: " + id + "não encontrado");
        }
    }

    public ProgramaTreinamento atualizarProgramaTreinamento(ProgramaTreinamento programaTreinamento) {
        Optional<ProgramaTreinamento> programaTreinamentoOptional = programaTreinamentoRepository.findById(programaTreinamento.getId());
        if (programaTreinamentoOptional.isPresent()) {
            return programaTreinamentoRepository.save(programaTreinamento);
        }else {
            throw new EntidadeNaoEncontradaException("Programa de Treinamento de ID: " + programaTreinamento.getId() + "não encontrado");
        }
    }
}
