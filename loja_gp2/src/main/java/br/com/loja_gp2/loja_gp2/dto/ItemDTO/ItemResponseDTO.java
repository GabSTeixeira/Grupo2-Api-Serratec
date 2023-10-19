package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;

public class ItemResponseDTO extends ItemBaseDTO {
    private long id;
    private ProdutoResponseDTO produto;
    private double valorBruto;
    private double valorLiquido;

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
    
    public double getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(double valorLiquido) {
        this.valorLiquido = valorLiquido;
    }
    
}
