package br.com.loja_gp2.loja_gp2.model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;

public enum EnumTipoAlteracaoLog {
    CREATE, UPDATE, DELETE;

    @JsonCreator
    public static EnumTipoAlteracaoLog fromString(String value) throws ResourceBadRequestException {
        for (EnumTipoAlteracaoLog alteracaoLog : EnumTipoAlteracaoLog.values()) {
            if(alteracaoLog.name().equalsIgnoreCase(value)) {
                return alteracaoLog;
            }
        }

        throw new ResourceBadRequestException( "valor do campo Alteracao: "+value+" inválido");
    }
}
