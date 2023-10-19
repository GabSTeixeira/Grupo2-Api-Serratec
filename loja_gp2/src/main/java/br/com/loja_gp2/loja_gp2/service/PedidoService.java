package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.common.ObjetoToJson;
import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Item;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
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
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public List<PedidoResponseDTO> buscarTodosPedidos() {

        List<Pedido> listaPedido = pedidoRepository.findAll();

        List<PedidoResponseDTO> listaPedidoResponse = listaPedido.stream()
        .map(p -> modelMapper.map(p, PedidoResponseDTO.class)).collect(Collectors.toList());

        return listaPedidoResponse;
    }

    public PedidoResponseDTO buscarPedidoPorId(long id){
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(id);

        if(pedidoEncontrado.isEmpty()){
            throw new ResourceNotFoundException(id, "pedido");
        }

        return modelMapper.map(pedidoEncontrado.get(), PedidoResponseDTO.class);
    }


    @Transactional
    public PedidoResponseDTO cadastrarPedido (PedidoRequestDTO pedidoRequest) {

        // define o id como zero
        pedidoRequest.setId(0);
       
        pedidoRequest.getListaItens().forEach((item) -> {
            if(item.getQuantidade() <= 0) {
                throw new ResourceBadRequestException("Por favor não insira itens com quantidade menor ou igual a zero");
            }
        });
        
        // converte o pedido request pra pedido normal
        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);
        
        // busca o usuario para inserir no pedido
        UsuarioResponseDTO usuarioEncontradoResponse = usuarioService.buscarUsuarioPorId(pedido.getUsuario().getId());
        Usuario usuario = modelMapper.map(usuarioEncontradoResponse, Usuario.class);
        
        // define as informações do pedido
        pedido.setUsuario(usuario);
        pedido.setDataPedido(new Date());
        
        List<ItemResponseDTO> listaItensResponse;
        
        try {  
            
            // calcula todas as informações individuais dos itens
            List<Item> itensParaCalculo = itemService.atualizarListaItens(pedidoRequest);
            
            // verifica se não tem nada negativo e calcula os totais individuais dos itens
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

        emailService.criarEmailPedido(usuario, pedido);
        
        PedidoResponseDTO pedidoResponse = modelMapper.map(pedido, PedidoResponseDTO.class);
        pedidoResponse.setListaItens(listaItensResponse);

        return pedidoResponse;

    }



}
