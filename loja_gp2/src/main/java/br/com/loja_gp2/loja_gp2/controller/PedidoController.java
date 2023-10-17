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

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAll() {

        List<PedidoResponseDTO> listaPedido = pedidoService.buscarTodosPedidos();

        return ResponseEntity.ok(listaPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getById(@PathVariable Long id) {

        PedidoResponseDTO pedidoEncontrado = pedidoService.buscarPedidoPorId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(pedidoEncontrado);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> postOne (@RequestBody PedidoRequestDTO pedido) {
        PedidoResponseDTO pedidoCriado = pedidoService.cadastrarPedido(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }   
}
