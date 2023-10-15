package br.com.loja_gp2.loja_gp2.model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;

public enum EnumTipoPagamento {
    CREDITO, AVISTA, BOLETO;

    @JsonCreator
    public static EnumTipoPagamento fromString(String value) throws ResourceBadRequestException {
        for (EnumTipoPagamento pagamento : EnumTipoPagamento.values()) {
            if(pagamento.name().equalsIgnoreCase(value)) {
                return pagamento;
            }
        }

        throw new ResourceBadRequestException( "valor do campo pagamento: "+value+" inválido");
    }
}
