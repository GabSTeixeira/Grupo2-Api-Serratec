package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

public abstract class ItemBaseDTO {
    private double quantidade;
    private double desconto;
    private double acrescimo;
    
    public double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(double quantidade) {
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
