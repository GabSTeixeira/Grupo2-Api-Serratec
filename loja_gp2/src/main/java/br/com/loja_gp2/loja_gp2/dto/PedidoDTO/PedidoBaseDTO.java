package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPagamento;

public abstract class PedidoBaseDTO {
    private long id;
    private EnumTipoPagamento formaPagamento;
    private double acrescimoPedido;
    private double descontoPedido;
    private String observacao;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public EnumTipoPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(EnumTipoPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public double getAcrescimoPedido() {
        return acrescimoPedido;
    }

    public void setAcrescimoPedido(double acrescimoPedido) {
        this.acrescimoPedido = acrescimoPedido;
    }

    public double getDescontoPedido() {
        return descontoPedido;
    }

    public void setDescontoPedido(double descontoPedido) {
        this.descontoPedido = descontoPedido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
