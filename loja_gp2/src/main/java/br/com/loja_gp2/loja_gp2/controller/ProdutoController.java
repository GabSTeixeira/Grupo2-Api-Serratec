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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(
        summary = "Retorna todos os produtos",
        description = "Esta requisição obtem todos os produtos"
    )
     @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição")

    })
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {

        List<ProdutoResponseDTO> listaProduto = produtoService.buscarTodosProdutos();
        
        return ResponseEntity.ok(listaProduto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtem por id",
        description = "Esta requisição obtem o produto por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição")

    })
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Long id) {

        ProdutoResponseDTO produtoEncontrado = produtoService.buscarProdutoPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
    }

    @GetMapping("/categoria/{id}")
    @Operation(
        summary = "Obtem por id",
        description = "Esta requisição obtem o produto por id"
    )
    public ResponseEntity<List<ProdutoResponseDTO>> findByCategoria(@PathVariable Long id) {

        List<ProdutoResponseDTO> listaProduto = produtoService.buscarProdutosPorCategoria(id);

        return ResponseEntity.ok(listaProduto);
    }

    @GetMapping("/ativos")  
    public ResponseEntity<List<ProdutoResponseDTO>> getAllActive() {

        List<ProdutoResponseDTO> listaProdutosAtivos = produtoService.buscarTodasProdutosAtivos();

        return ResponseEntity.status(HttpStatus.OK).body(listaProdutosAtivos);
    }


    @GetMapping("/inativas")  
    public ResponseEntity<List<ProdutoResponseDTO>> getAllInactive() {

        List<ProdutoResponseDTO> listaProdutosInativos = produtoService.buscarTodasProdutosInativos();

        return ResponseEntity.status(HttpStatus.OK).body(listaProdutosInativos);
    }

    @PostMapping
    @Operation(
        summary = "Adicionar",
        description = "Esta requisição adiciona um produto"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto Criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<ProdutoResponseDTO> postOne(@RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoCriado = produtoService.cadastrarProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar por id",
        description = "Esta requisição atualiza o produto por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")

    })
    public ResponseEntity<ProdutoResponseDTO> putOne(@PathVariable Long id, @RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoAlterado = produtoService.alterarProduto(id,produto);

        return ResponseEntity.status(HttpStatus.OK).body(produtoAlterado);
    }

    @PutMapping("/desativar/{id}")
    @Operation(
        summary = "Desativa por id",
        description = "Esta requisição desativa o produto por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        produtoService.inativarProduto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

     @PutMapping("/ativar/{id}")
     @Operation(
        summary = "Ativa por id",
        description = "Esta requisição ativa o produto por id"
    )
     @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        produtoService.reativarProduto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}