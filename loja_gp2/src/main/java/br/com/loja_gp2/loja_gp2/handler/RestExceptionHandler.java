package br.com.loja_gp2.loja_gp2.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.loja_gp2.loja_gp2.common.ConversorData;
import br.com.loja_gp2.loja_gp2.model.error.ErrorResposta;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceForbiddenException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceGatewayTimeoutException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceUnauthorizedException;

@ControllerAdvice
public class RestExceptionHandler {
    

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResposta> handlerResourceNotFoundException(ResourceNotFoundException ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(404, "Not Found", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResposta> handlerResourceBadRequestException(ResourceBadRequestException ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(400, "Bad Request", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceUnauthorizedException.class)
    public ResponseEntity<ErrorResposta> handlerResourceUnauthorizedException(ResourceUnauthorizedException ex){
        
        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(401, "Unauthorized", ex.getMessage(), data);

        return new ResponseEntity<>(erro,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorResposta> handlerResourceForbiddenException(ResourceForbiddenException ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(403, "Forbidden", ex.getMessage(), data);

        return new ResponseEntity<>(erro,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceGatewayTimeoutException.class)
    public ResponseEntity<ErrorResposta> handlerResourceGatewayTimeoutException(ResourceGatewayTimeoutException ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(504, "Gateway Timeout", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.GATEWAY_TIMEOUT);
    }
    
    @ExceptionHandler(ResourceInternalServerErrorException.class)
    public ResponseEntity<ErrorResposta> handlerResourceInternalServerErrorException (ResourceInternalServerErrorException ex) {
        
        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(500, "Internal Server Error", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResposta> handlerException(Exception ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(500, "Internal Server Error", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}