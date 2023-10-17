package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;

public class ItemRequestDTO extends ItemBaseDTO {
    private ProdutoRequestDTO produto;
    private double descontoPedido;
    private double acrescimoPedido;

    public double getDescontoPedido() {
        return descontoPedido;
    }

    public void setDescontoPedido(double descontoPedido) {
        this.descontoPedido = descontoPedido;
    }

    public double getAcrescimoPedido() {
        return acrescimoPedido;
    }

    public void setAcrescimoPedido(double acrescimoPedido) {
        this.acrescimoPedido = acrescimoPedido;
    }

    public ProdutoRequestDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoRequestDTO produto) {
        this.produto = produto;
    }
}
