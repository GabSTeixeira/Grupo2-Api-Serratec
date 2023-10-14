package br.com.loja_gp2.loja_gp2.dto.ItemDTO;

import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;

public class ItemRequestDTO extends ItemBaseDTO {
    private PedidoRequestDTO pedido;
    private ProdutoRequestDTO produto;
    
    public PedidoRequestDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoRequestDTO pedido) {
        this.pedido = pedido;
    }

    public ProdutoRequestDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoRequestDTO produto) {
        this.produto = produto;
    }
}
