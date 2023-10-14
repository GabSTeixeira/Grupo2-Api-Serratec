package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceBadRequestException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceBadRequestException(String mensagem){
        super(mensagem);
    }
}
