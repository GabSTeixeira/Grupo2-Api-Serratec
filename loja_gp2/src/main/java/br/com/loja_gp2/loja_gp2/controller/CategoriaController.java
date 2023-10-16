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

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.service.CategoriaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> getAll() {

        List<CategoriaResponseDTO> listaCategoria = categoriaService.buscarTodasCategorias();

        return ResponseEntity.ok(listaCategoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Long id) {

        CategoriaResponseDTO categoriaEncontrada = categoriaService.buscarCategoriaPorId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(categoriaEncontrada);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> postOne(@RequestBody CategoriaResponseDTO categoria) {
        CategoriaResponseDTO categoriaCriada = categoriaService.cadastrarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> inativeOne(@PathVariable Long id){
        categoriaService.inativarCategoria(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> activateOne(@PathVariable Long id){
        categoriaService.reativarCategoria(id);
        
        return ResponseEntity.ok().build();
    }
}
