package br.com.loja_gp2.loja_gp2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Item;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;
import br.com.loja_gp2.loja_gp2.repository.ItemRepository;

@Service
public class ItemService {
    

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    
    // de fato remove do estoque de produto e cadastra na tabela de itens
    
    public List<ItemResponseDTO> cadastrarItensPedido (Pedido pedido) {
        List<Item> listaItens;

        try {

            // tira do estoque e cadastra o pedido nos itens
                pedido.getListaItens().forEach((item) -> {
                item.setPedido(pedido);
                produtoService.retirarEstoque(item.getProduto().getId(), item.getQuantidade());
            });
            
            listaItens = itemRepository.saveAll(pedido.getListaItens());
        
        } catch (Exception e) {
            throw new ResourceBadRequestException("Não foi possivel cadastrar os itens deste pedido");
        }

        return listaItens.stream().map(i -> modelMapper.map(i, ItemResponseDTO.class))
        .collect(Collectors.toList());
    }


    // verifica se a compra é possivel e atualiza os valores de itens
    public List<Item> atualizarListaItens (PedidoRequestDTO pedidoRequest) {
        List<Item> listaItens = new ArrayList<>();

        for (ItemRequestDTO itemReq : pedidoRequest.getListaItens()) {
            
            if (itemReq.getQuantidade() > produtoService.verificarEstoque(itemReq.getProduto().getId())) {
                 throw new ResourceBadRequestException("Item", "Estoque indisponivel para o produto com Id: "+itemReq.getProduto().getId());
            }
            

            ProdutoResponseDTO produtoAtualizado = produtoService.buscarProdutoPorId(itemReq.getProduto().getId());
            produtoAtualizado.setEstoque(produtoAtualizado.getEstoque() - itemReq.getQuantidade());
            Item item = modelMapper.map(itemReq, Item.class);
            
            item.setProduto(modelMapper.map(produtoAtualizado, Produto.class));
            
            // verifica se não é possivel realizar a venda para aquele produto    
            if (produtoService.buscarProdutoPorId(itemReq.getProduto().getId()).getCategoria().isStatus() == false) {
                throw new ResourceBadRequestException("Item", "Categoria não esta disponivel para o produto com Id: "+ itemReq.getProduto().getId());
            }

            listaItens.add(item);
        }

        return listaItens;
    } 
}
