package br.com.loja_gp2.loja_gp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;
import br.com.loja_gp2.loja_gp2.service.ProdutoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
     @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {

        List<ProdutoResponseDTO> listaProduto = produtoService.buscarTodosProdutos();
        
        return ResponseEntity.ok(listaProduto);
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Long id) {

        ProdutoResponseDTO produtoEncontrado = produtoService.buscarProdutoPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProdutoResponseDTO>> findByCategoria(@PathVariable Long id) {

        List<ProdutoResponseDTO> listaProduto = produtoService.buscarProdutosPorCategoria(id);

        return ResponseEntity.ok(listaProduto);
    }

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto Criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
    })
    public ResponseEntity<ProdutoResponseDTO> postOne(@RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoCriado = produtoService.cadastrarProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponseDTO> putOne(@PathVariable Long id, @RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoAlterado = produtoService.alterarProduto(id,produto);

        return ResponseEntity.status(HttpStatus.OK).body(produtoAlterado);
    }

    @PutMapping("/desativar/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        produtoService.inativarProduto(id);

        return ResponseEntity.ok().build();
    }

     @PutMapping("/ativar/{id}")
     @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        produtoService.reativarProduto(id);

        return ResponseEntity.ok().build();
    }
}