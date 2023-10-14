package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.List;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
public class PedidoRequestDTO extends PedidoBaseDTO {
    private UsuarioRequestDTO usuario;
    private List<ItemRequestDTO> listaItens;
 
    public UsuarioRequestDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRequestDTO usuario) {
        this.usuario = usuario;
    }

    public List<ItemRequestDTO> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<ItemRequestDTO> listaItens) {
        this.listaItens = listaItens;
    }
    
}
