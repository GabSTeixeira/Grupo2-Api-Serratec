package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.Date;

import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPagamento;

public abstract class PedidoBaseDTO {
    private long id;
    private Date dataPedido;
    private EnumTipoPagamento formaPagamento;
    private double descontoTotal;
    private double acrescimoTotal;
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
    public double getDescontoTotal() {
        return descontoTotal;
    }
    public void setDescontoTotal(double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }
    public double getAcrescimoTotal() {
        return acrescimoTotal;
    }
    public void setAcrescimoTotal(double acrescimoTotal) {
        this.acrescimoTotal = acrescimoTotal;
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
