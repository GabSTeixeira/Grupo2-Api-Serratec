package br.com.loja_gp2.loja_gp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.service.CategoriaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria retornada com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<List<CategoriaResponseDTO>> getAll() {

        List<CategoriaResponseDTO> listaCategoria = categoriaService.buscarTodasCategorias();

        return ResponseEntity.ok(listaCategoria);
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Long id) {

        CategoriaResponseDTO categoriaEncontrada = categoriaService.buscarCategoriaPorId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(categoriaEncontrada);
    }
    

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoria Criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
    })
    public ResponseEntity<CategoriaResponseDTO> postOne(@RequestBody CategoriaResponseDTO categoria) {
        CategoriaResponseDTO categoriaCriada = categoriaService.cadastrarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado")
    })
    public ResponseEntity<CategoriaResponseDTO> putOne(@PathVariable long id, @RequestBody CategoriaRequestDTO categoria) {
       CategoriaResponseDTO categoriaAlterada = categoriaService.alterarCategoria(id, categoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoriaAlterada);
    }

    @PutMapping("/desativar/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        categoriaService.inativarCategoria(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/ativar/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        categoriaService.reativarCategoria(id);
        
        return ResponseEntity.ok().build();
    }
}
