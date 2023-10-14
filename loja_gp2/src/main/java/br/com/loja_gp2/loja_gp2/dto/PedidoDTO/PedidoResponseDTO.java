package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.Date;

public class PedidoResponseDTO extends PedidoRequestDTO {
    private long id;
    private Date dataPedido;
    private double valorTotal;
    private double descontoTotal;
    private double acrescimoTotal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }

    public double getAcrescimoTotal() {
        return acrescimoTotal;
    }

    public void setAcrescimoTotal(double acrescimoTotal) {
        this.acrescimoTotal = acrescimoTotal;
    }
    
    
}
