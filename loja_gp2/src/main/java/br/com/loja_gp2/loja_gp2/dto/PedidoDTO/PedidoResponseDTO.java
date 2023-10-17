package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.List;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;

public class PedidoResponseDTO extends PedidoBaseDTO {
    
    private UsuarioResponseDTO usuario;
    private List<ItemResponseDTO> listaItens;
    
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
