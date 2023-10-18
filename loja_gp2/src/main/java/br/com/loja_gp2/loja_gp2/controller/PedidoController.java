package br.com.loja_gp2.loja_gp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.PedidoDTO.PedidoResponseDTO;
import br.com.loja_gp2.loja_gp2.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(
        summary = "Retorna todos os pedidos",
        description = "Esta requisição obtem todos os pedidos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido Criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
    })
    public ResponseEntity<List<PedidoResponseDTO>> getAll() {

        List<PedidoResponseDTO> listaPedido = pedidoService.buscarTodosPedidos();

        return ResponseEntity.ok(listaPedido);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtem por id",
        description = "Esta requisição obtem o pedido por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> getById(@PathVariable Long id) {

        PedidoResponseDTO pedidoEncontrado = pedidoService.buscarPedidoPorId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(pedidoEncontrado);
    }

    @PostMapping
    @Operation(
        summary = "Adicionar",
        description = "Esta requisição adiciona um pedido"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido retornados com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<PedidoResponseDTO> postOne (@RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoResponseDTO pedidoResponse = pedidoService.cadastrarPedido(pedidoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponse);
    }   
}
