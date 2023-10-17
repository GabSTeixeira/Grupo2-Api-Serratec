package br.com.loja_gp2.loja_gp2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.TransactionScoped;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    
    public List<ItemResponseDTO> cadastrarItensPedido (Pedido pedido) {
        List<Item> listaItens = new ArrayList<>();

        for (Item item : pedido.getListaItens()) {
            
            // verifica se não é possivel realizar a venda para aquele produto    
            if (item.getQuantidade() > produtoService.verificarEstoque(item.getProduto().getId())) {
                 throw new ResourceBadRequestException("Item", "Estoque indisponivel para o produto com Id: "+item.getProduto().getId());
            }
                     
            // retirar do estoque de produto.  
            ProdutoResponseDTO produtoAtualizado = produtoService.retirarEstoque(item.getProduto().getId(), item.getQuantidade());
            
            item.setProduto(modelMapper.map(produtoAtualizado, Produto.class));
            item.setPedido(pedido);

            listaItens.add(item);
        }

        try {
            
            listaItens = itemRepository.saveAll(listaItens);
            
        } catch (Exception e) {
            throw new ResourceBadRequestException("Não foi possivel cadastrar os itens deste pedido");
        }

        return listaItens.stream().map(i -> modelMapper.map(i, ItemResponseDTO.class))
        .collect(Collectors.toList());
    }
}
