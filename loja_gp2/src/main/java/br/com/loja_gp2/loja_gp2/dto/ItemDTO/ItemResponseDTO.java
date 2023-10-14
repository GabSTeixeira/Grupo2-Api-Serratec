package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

public class ItemResponseDTO extends ItemRequestDTO {
    private long id;
    private double valorTotal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
