package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceGatewayTimeoutException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceGatewayTimeoutException(String mensagem){
        super(mensagem);
    }

}
