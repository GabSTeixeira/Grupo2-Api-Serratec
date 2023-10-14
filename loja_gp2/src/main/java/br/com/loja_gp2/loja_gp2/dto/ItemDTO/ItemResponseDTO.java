package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;

public class ItemResponseDTO extends ItemBaseDTO {
    private long id;
    private ProdutoResponseDTO produto;
    private double valorTotal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProdutoResponseDTO getProduto () {
        return produto;
    }

    public void setProduto (ProdutoResponseDTO produto) {
        this.produto = produto;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
