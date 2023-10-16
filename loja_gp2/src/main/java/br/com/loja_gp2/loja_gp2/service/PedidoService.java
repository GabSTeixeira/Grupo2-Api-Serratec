package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        pedidoRequest.setId(0);
        
        // cadastra os intens na tabela intem
        
        
        UsuarioResponseDTO usuarioEncontradoResponse = usuarioService.buscarUsuarioPorId(pedidoRequest.getId());
        Usuario usuario = modelMapper.map(usuarioEncontradoResponse, Usuario.class);
        
        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);
        
        pedido.setUsuario(usuario);
        pedido.setDataPedido(new Date());
        
        
        try {
            List<ItemResponseDTO> listaItensResponse = itemService.cadastrarItensPedido(pedidoRequest, pedidoRequest.getListaItens());
            
            List<Item> listaItens = listaItensResponse.stream().map(i-> modelMapper.map(i, Item.class)).collect(Collectors.toList());
            
            pedido.setListaItens(listaItens);

            pedido.calcularTotais();

            pedido = pedidoRepository.save(pedido);

        } catch (Exception e) {
            throw new ResourceBadRequestException(Pedido.class.getSimpleName(), "Não foi possivel cadastrar o pedido");
        }
        
        return modelMapper.map(pedido, PedidoResponseDTO.class);

    }


}
