package br.com.fiap.esg_restful.service;

import br.com.fiap.esg_restful.model.IndicadoresDepartamento;
import br.com.fiap.esg_restful.repository.IndicadoresDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IndicadoresDepartamentoService {
    @Autowired
    private IndicadoresDepartamentoRepository indicadoresDepartamentoRepository;

    public Page<IndicadoresDepartamento> listarIndicadores(Pageable paginacao) {
        indicadoresDepartamentoRepository.atualizarIndicadoresDepartamento();
        return indicadoresDepartamentoRepository.findAll(paginacao);
    }
}
