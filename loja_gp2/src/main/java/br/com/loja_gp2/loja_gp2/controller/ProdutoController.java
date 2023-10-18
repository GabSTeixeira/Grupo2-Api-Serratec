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


@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {

        List<ProdutoResponseDTO> listaProduto = produtoService.buscarTodosProdutos();
        
        return ResponseEntity.ok(listaProduto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Long id) {

        ProdutoResponseDTO produtoEncontrado = produtoService.buscarProdutoPorId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(produtoEncontrado);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> postOne(@RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoCriado = produtoService.cadastrarProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> putOne(@PathVariable Long id, @RequestBody ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoAlterado = produtoService.alterarProduto(id,produto);

        return ResponseEntity.status(HttpStatus.OK).body(produtoAlterado);
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        produtoService.inativarProduto(id);

        return ResponseEntity.ok().build();
    }

     @PutMapping("/ativar/{id}")
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        produtoService.reativarProduto(id);

        return ResponseEntity.ok().build();
    }
}