package br.com.loja_gp2.loja_gp2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.repository.LogRepository;

@Service
public class LogService {
    
    @Autowired
    private LogRepository logRepository;

    /**
     * Delega um registro de um Log/auditoria no banco de dados para o Repository.
     * @param log
     */
    public void registrarLog (Log log) {
        try {
            logRepository.save(log);
            
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }
    }
}
