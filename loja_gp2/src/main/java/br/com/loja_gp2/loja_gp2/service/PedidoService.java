package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.ItemDTO.ItemResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPerfil;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
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
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Delega uma busca de uma lista com todos os pedidos dentro do banco de dados para o Repository.
     * @return Uma lista de PedidoResponseDTO
     */
    public List<PedidoResponseDTO> buscarTodosPedidos() {

        List<Pedido> listaPedido = pedidoRepository.findAll();

        List<PedidoResponseDTO> listaPedidoResponse = listaPedido.stream()
        .map(p -> modelMapper.map(p, PedidoResponseDTO.class)).collect(Collectors.toList());

        return listaPedidoResponse;
    }

    /**
     * Delega uma busca de um pedido por id no banco de dados para o Repository.
     * @param id
     * @return Uma PedidoResponseDTO
     */
    public PedidoResponseDTO buscarPedidoPorId(long id){
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(id);


        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // verifica se o pedido existe ou se o pedido é do cliente que esta o solicitando, caso não dispara uma exception
        if(pedidoEncontrado.isEmpty() || (usuario.getPerfil().compareTo(EnumTipoPerfil.CLIENTE) == 0 && pedidoEncontrado.get().getUsuario().getId() != usuario.getId())){
            throw new ResourceNotFoundException(id, Pedido.class.getSimpleName());
        }

        return modelMapper.map(pedidoEncontrado.get(), PedidoResponseDTO.class);
    }

    /**
     * Delega um cadastro de um pedido no bando de dados para o Repository.
     * @return Um PedidoResponseDTO
     */
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
        
       
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // define as informações do pedido
        pedido.setUsuario(usuario);
        pedido.setDataPedido(new Date());
        
        List<ItemResponseDTO> listaItensResponse;
        
        // verifica se é possivel a venda destes itens e transfere pra uma lista normal
        List<Item> itensParaCalculo = itemService.atualizarListaItens(pedidoRequest);
        
        // verifica se não tem nada negativo e calcula os totais individuais dos itens
        itensParaCalculo.forEach(i -> i.calcularValorTotal());
        
        // define a lista atualizada e depois calcula as informações do pedido
        pedido.setListaItens(itensParaCalculo);
        pedido.calcularTotais();
        
        try {  
            
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
