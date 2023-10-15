package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceUnauthorizedException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceUnauthorizedException(String mensagem){
        super(mensagem);
    } 
}
