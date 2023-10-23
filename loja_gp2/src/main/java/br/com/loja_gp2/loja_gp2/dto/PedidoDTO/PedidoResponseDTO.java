package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.Date;
import java.util.List;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;

public class PedidoResponseDTO extends PedidoBaseDTO {
    
    private UsuarioResponseDTO usuario;
    private Date dataPedido;
    private double descontoItens;
    private double acrescimoItens;
    private double valorBruto;
    private double valorLiquido;
    private List<ItemResponseDTO> listaItens;
    
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public double getDescontoItens() {
        return descontoItens;
    }
    public void setDescontoItens(double descontoItens) {
        this.descontoItens = descontoItens;
    }
    public double getAcrescimoItens() {
        return acrescimoItens;
    }
    public void setAcrescimoItens(double acrescimoItens) {
        this.acrescimoItens = acrescimoItens;
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
    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
    public List<ItemResponseDTO> getListaItens() {
        return listaItens;
    }
    public void setListaItens(List<ItemResponseDTO> listaItens) {
        this.listaItens = listaItens;
    }
    
    
}
