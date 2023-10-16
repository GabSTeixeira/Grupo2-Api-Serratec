package br.com.loja_gp2.loja_gp2.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
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


    public List<ItemResponseDTO> cadastrarItensPedido (Pedido pedido, List<ItemRequestDTO> listaItensReq) {
        List<Item> listaItens = new ArrayList<>();
        
        for (ItemRequestDTO itemReq : listaItensReq) {
            
            // verifica se não é possivel realizar a venda para aquele produto
            /*
             * 
             if (itemReq.getQuantidade() > produtoService.verificarEstoque(itemReq.getProduto()) {
                 throw new ResourceBadRequestException("Item", "Estoque indisponivel para o produto com Id: "+itemReq.getProduto().getId());
                }
            */

            Produto produtoDoItem = modelMapper.map(itemReq.getProduto(), Produto.class);
            Item item = modelMapper.map(itemReq, Item.class);
            

            item.setProduto(produtoDoItem);

            // retirar do estoque de produto. o estoque de produto dentro de item vai estar desatualizado, discutir se deve ou não atualizar com os outros
            //produtoService.retirarEstoque(item.getQuantidade());





           // listaItensResponse.add()


           
        }
        
        return null;



    }
}
