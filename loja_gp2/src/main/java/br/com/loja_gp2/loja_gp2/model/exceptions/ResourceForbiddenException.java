package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceForbiddenException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceForbiddenException(String mensagem){
        super(mensagem);
    }

}
