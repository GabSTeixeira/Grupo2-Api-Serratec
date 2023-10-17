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
import org.springframework.web.bind.annotation.RequestBody;

import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {

        List<UsuarioResponseDTO> listaUsuario = usuarioService.buscarTodosUsuario();

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> getOneById(@PathVariable long id) {
        UsuarioResponseDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(usuarioEncontrado);
    }
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario Criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> postOne(@RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> putOne(@PathVariable long id, @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioAlterado = usuarioService.alterarUsuario(id, usuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAlterado);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> inativeOne(@PathVariable long id) {
        usuarioService.inativarUsuario(id);

        return ResponseEntity.ok().build();
    }
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> activeOne(@PathVariable long id) {
        usuarioService.retivarUsuario(id);

        return ResponseEntity.ok().build();
    }
}