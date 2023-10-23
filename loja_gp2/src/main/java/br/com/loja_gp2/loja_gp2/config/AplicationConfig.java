package br.com.loja_gp2.loja_gp2.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AplicationConfig {
   
    /**
     * Método feito para que o Spring enxergue o ModelMapper sem a necessidade de instanciá-lo. 
     * @return Um objeto instanciado do tipo ModelMapper.
     */
    @Bean 
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }
}
