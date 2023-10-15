package br.com.loja_gp2.loja_gp2.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.repository.LogRepository;

@Service
public class LogService {
    
    @Autowired
    private LogRepository logRepository;

    
    public void registrarLog (Log log) {
        
        try {
            logRepository.save(log);
        } catch(Exception e) {
            throw new ResourceInternalServerErrorException();
        }
    }
}
