package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.model.TreinamentoColaborador;
import br.com.fiap.esg_restful.repository.TreinamentoColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TreinamentoColaboradorService {
    @Autowired
    private TreinamentoColaboradorRepository treinamentoColaboradorRepository;

    public Page<TreinamentoColaborador> listarTreinamentoColaborador(Pageable paginacao) {
        return treinamentoColaboradorRepository.findAll(paginacao);
    }
}
