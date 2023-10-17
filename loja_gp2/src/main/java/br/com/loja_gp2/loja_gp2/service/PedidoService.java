package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Item;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.PedidoRepository;

@Service
public class PedidoService {
    
    //alou
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired 
    private ItemService itemService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public PedidoResponseDTO cadastrarPedido (PedidoRequestDTO pedidoRequest) {

        // define o id como zero
        pedidoRequest.setId(0);
        
        //converte o pedido request pra pedido normal
        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);
        
        // busca o usuario para inserir no pedido
        UsuarioResponseDTO usuarioEncontradoResponse = usuarioService.buscarUsuarioPorId(pedido.getUsuario().getId());
        Usuario usuario = modelMapper.map(usuarioEncontradoResponse, Usuario.class);
        
        //define as coisas do pedido
        pedido.setUsuario(usuario);
        pedido.setDataPedido(new Date());
        
        List<ItemResponseDTO> listaItensResponse;
        
        try {  
            
            
            // calcula todas as informações individuais dos itens
            List<Item> itensParaCalculo = pedidoRequest.getListaItens().stream()
            .map(i -> modelMapper.map(i, Item.class)).collect(Collectors.toList());
            
            // verifica se não tem nada negativo ou nulo na lista de itens
            
            itensParaCalculo.forEach(i -> i.calcularValorTotal());
            
            // define a lista atualizada e depois calcula as informações do pedido
            pedido.setListaItens(itensParaCalculo);
            pedido.calcularTotais();

            // salva o pedido e os itens deste pedido nas devidas tabelas
            pedido = pedidoRepository.save(pedido); 
            listaItensResponse = itemService.cadastrarItensPedido(pedido);
        
            
        } catch (Exception e) {
            throw new ResourceBadRequestException(Pedido.class.getSimpleName(), "Não foi possivel cadastrar o pedido");
        }
        
        PedidoResponseDTO pedidoResponse = modelMapper.map(pedido, PedidoResponseDTO.class);
        pedidoResponse.setListaItens(listaItensResponse);

        return pedidoResponse;

    }


}
