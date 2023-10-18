package br.com.loja_gp2.loja_gp2.service;

import javax.transaction.TransactionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.repository.LogRepository;

@Service
public class LogService {
    
    @Autowired
    private LogRepository logRepository;

    public void registrarLog (Log log) throws ResourceInternalServerErrorException {
        logRepository.save(log);
    }
}
