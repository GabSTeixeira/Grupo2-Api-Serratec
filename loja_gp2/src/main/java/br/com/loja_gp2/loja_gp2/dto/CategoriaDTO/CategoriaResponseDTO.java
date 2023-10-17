package br.com.loja_gp2.loja_gp2.dto.CategoriaDTO;

public class CategoriaResponseDTO extends CategoriaRequestDTO {
   
    private boolean status;
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
