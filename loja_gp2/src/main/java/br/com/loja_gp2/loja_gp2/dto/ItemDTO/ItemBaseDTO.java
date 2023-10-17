package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

public abstract class ItemBaseDTO {
    private long quantidade;
    private double desconto;
    private double acrescimo;
    
    public long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
    public double getAcrescimo() {
        return acrescimo;
    }
    public void setAcrescimo(double acrescimo) {
        this.acrescimo = acrescimo;
    }


    
}
