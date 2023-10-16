package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.PedidoRepository;

@Service
public class PedidoService {
    

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
        
        UsuarioResponseDTO usuarioEncontradoResponse = usuarioService.buscarUsuarioPorId(pedidoRequest.getId());
        Usuario usuario = modelMapper.map(usuarioEncontradoResponse, Usuario.class);

        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);

        pedido.setUsuario(usuario);
        pedido.setDataPedido(new Date());
        
        

        return null;

    }


}
