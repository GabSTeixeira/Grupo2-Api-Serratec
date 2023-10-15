package br.com.loja_gp2.loja_gp2.model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;

public enum EnumTipoPerfil {
    ADMIN, CLIENTE;


    @JsonCreator
    public static EnumTipoPerfil fromString(String value) throws ResourceBadRequestException {
        for (EnumTipoPerfil perfil : EnumTipoPerfil.values()) {
            if(perfil.name().equalsIgnoreCase(value)) {
                return perfil;
            }
        }

        throw new ResourceBadRequestException( "valor do campo perfil: "+value+" inválido");
    }
}
