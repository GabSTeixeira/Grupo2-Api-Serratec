package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import java.util.List;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemRequestDTO;
public class PedidoRequestDTO extends PedidoBaseDTO {
    
    private List<ItemRequestDTO> listaItens;

    public List<ItemRequestDTO> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<ItemRequestDTO> listaItens) {
        this.listaItens = listaItens;
    }
    
}
