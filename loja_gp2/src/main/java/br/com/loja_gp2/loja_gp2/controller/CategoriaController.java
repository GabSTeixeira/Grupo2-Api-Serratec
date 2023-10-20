package br.com.loja_gp2.loja_gp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Retorna todas as categorias",
        description = "Esta requisição obtem todas as categorias"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado")
    })
    public ResponseEntity<List<CategoriaResponseDTO>> getAll() {

        List<CategoriaResponseDTO> listaCategoria = categoriaService.buscarTodasCategorias();

        return ResponseEntity.ok(listaCategoria);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtem por id",
        description = "Esta requisição obtem a categoria por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição")

    })
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Long id) {

        CategoriaResponseDTO categoriaEncontrada = categoriaService.buscarCategoriaPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoriaEncontrada);
    }
    
    @GetMapping("/ativas")  
    @Operation(
        summary = "Retorna todas as ativas",
        description = "Esta requisição busca todas as categorias ativas"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categorias ativas encontradas com sucesso"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<List<CategoriaResponseDTO>> getAllActive() {

        List<CategoriaResponseDTO> listaCategoriasAtivas = categoriaService.buscarTodasCategoriasAtivas();

        return ResponseEntity.status(HttpStatus.OK).body(listaCategoriasAtivas);
    }

    @GetMapping("/inativas")  
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Retorna todas as inativas",
        description = "Esta requisição busca todas as categorias inativas"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categorias inativas encontradas com sucesso"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<List<CategoriaResponseDTO>> getAllInactive() {

        List<CategoriaResponseDTO> listaCategoriasInativas = categoriaService.buscarTodasCategoriasInativas();

        return ResponseEntity.status(HttpStatus.OK).body(listaCategoriasInativas);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Adicionar",
        description = "Esta requisição adiciona uma categoria"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoria Criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")

    })
    public ResponseEntity<CategoriaResponseDTO> postOne(@RequestBody CategoriaResponseDTO categoria) {
        CategoriaResponseDTO categoriaCriada = categoriaService.cadastrarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Atualizar por id",
        description = "Esta requisição atualiza a categoria por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<CategoriaResponseDTO> putOne(@PathVariable long id, @RequestBody CategoriaRequestDTO categoria) {
       CategoriaResponseDTO categoriaAlterada = categoriaService.alterarCategoria(id, categoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoriaAlterada);
    }

    @PutMapping("/desativar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Desativa por id",
        description = "Esta requisição desativa a categoria por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        categoriaService.inativarCategoria(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/ativar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Ativa por id",
        description = "Esta requisição ativa a categoria por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        categoriaService.reativarCategoria(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
