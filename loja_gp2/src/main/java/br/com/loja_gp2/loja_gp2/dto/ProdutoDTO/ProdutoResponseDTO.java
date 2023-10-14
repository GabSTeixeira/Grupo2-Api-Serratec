package br.com.loja_gp2.loja_gp2.dto.ProdutoDTO;

public class ProdutoResponseDTO extends ProdutoRequestDTO {
    private long id;
    private boolean status;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
