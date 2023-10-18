package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.Date;

import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPagamento;

public abstract class PedidoBaseDTO {
    private long id;
    private Date dataPedido;
    private EnumTipoPagamento formaPagamento;
    private double descontoItens;
    private double acrescimoItens;
    private double acrescimoPedido;
    private double descontoPedido;
    private double valorTotal;
    private String observacao;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public EnumTipoPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(EnumTipoPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public double getDescontoItens() {
        return descontoItens;
    }
    public void setDescontoItens(double descontoItens) {
        this.descontoItens = descontoItens;
    }
    public double getAcrescimoItens() {
        return acrescimoItens;
    }
    public void setAcrescimoItens(double acrescimoItens) {
        this.acrescimoItens = acrescimoItens;
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

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    
}
