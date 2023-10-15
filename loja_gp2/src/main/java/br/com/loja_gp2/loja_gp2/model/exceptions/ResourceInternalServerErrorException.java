package br.com.loja_gp2.loja_gp2.model.exceptions;

public class ResourceInternalServerErrorException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceInternalServerErrorException(){
        super("Um problema ocorreu durante a execução, verifique o input e tente novamente");
    }
}
