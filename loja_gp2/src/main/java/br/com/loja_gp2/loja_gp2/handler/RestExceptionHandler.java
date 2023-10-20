package br.com.loja_gp2.loja_gp2.handler;

import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResposta> handlerBadCredentialsException(Exception ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(401, "Unauthorized", "Usuário ou senha inválidos", data);

        return new ResponseEntity<>(erro, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResposta> handlerAccessDeniedException(AccessDeniedException ex){

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(403, "Forbidden", ex.getMessage(), data);

        return new ResponseEntity<>(erro, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResposta> handlerConstraintViolationException(Exception ex) {

        String data = ConversorData.converterDateParaDataHora(new Date());

        ErrorResposta erro = new ErrorResposta(400, "BadRequest", "Já existe um cadastro com essas informações.", data);

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }
}
