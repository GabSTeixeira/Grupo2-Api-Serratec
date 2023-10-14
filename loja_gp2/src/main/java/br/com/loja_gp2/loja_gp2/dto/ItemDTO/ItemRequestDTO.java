package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;

public class ItemRequestDTO {
    private Pedido pedido;
    private Produto produto;
    private double quantidade;
    private double desconto;
    private double acrescimo;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

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
