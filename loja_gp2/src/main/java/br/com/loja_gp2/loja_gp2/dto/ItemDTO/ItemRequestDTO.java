package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;

public class ItemRequestDTO extends ItemBaseDTO {
    private ProdutoRequestDTO produto;
    
    public ProdutoRequestDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoRequestDTO produto) {
        this.produto = produto;
    }
}
