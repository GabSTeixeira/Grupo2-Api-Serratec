package br.com.loja_gp2.loja_gp2.dto.ProdutoDTO;

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;

public class ProdutoResponseDTO extends ProdutoBaseDTO {
    private boolean status;
    private CategoriaResponseDTO categoria;
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }  
}
