package br.com.loja_gp2.loja_gp2.dto.ProdutoDTO;

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaRequestDTO;
public class ProdutoRequestDTO extends ProdutoBaseDTO {
    
    private CategoriaRequestDTO categoria;

    public CategoriaRequestDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRequestDTO categoria) {
        this.categoria = categoria;
    }   
}
