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

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {

        List<UsuarioResponseDTO> listaUsuario = usuarioService.buscarTodosUsuario();

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getOneById(@PathVariable long id) {
        UsuarioResponseDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        
        return ResponseEntity.status(HttpStatus.FOUND).body(usuarioEncontrado);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> postOne(@RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> putOne(@PathVariable long id, @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioAlterado = usuarioService.alterarUsuario(id, usuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAlterado);
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> inativeOne(@PathVariable long id) {
        usuarioService.inativarUsuario(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> activeOne(@PathVariable long id) {
        usuarioService.retivarUsuario(id);

        return ResponseEntity.ok().build();
    }
}
