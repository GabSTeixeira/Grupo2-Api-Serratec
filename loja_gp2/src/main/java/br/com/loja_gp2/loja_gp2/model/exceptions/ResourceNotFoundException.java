package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(long id, String elemento){
        super("Não foi possivel localizar o "+elemento+" com Id: "+ id);
    }
}
