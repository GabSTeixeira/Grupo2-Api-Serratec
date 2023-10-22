package br.com.loja_gp2.loja_gp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioLoginRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioLoginResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Retorna todos os usuarios",
        description = "Esta requisição obtem todos os usuários"
    )

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso" ),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {

        List<UsuarioResponseDTO> listaUsuario = usuarioService.buscarTodosUsuario();

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENTE')")
    @Operation(
        summary = "Obtem por id",
        description = "Esta requisição obtem o usuário por id, um cliente pode buscar seu proprio usuario pelo seu proprio id, porém apenas isso"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")

    })
    public ResponseEntity<UsuarioResponseDTO> getOneById(@PathVariable long id) {
        
        UsuarioResponseDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(usuarioEncontrado);
    }
    
    @GetMapping ("/ativos")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Retorna todos os ativos",
        description = "Esta requisição busca todos usuários ativos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios ativos encontrados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> getAllActive(){

        List<UsuarioResponseDTO> listaUsuario = usuarioService.buscarTodosUsuariosAtivos();

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
    }
    
    @GetMapping ("/inativos")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Retorna todos os inativos",
        description = "Esta requisição busca todos usuários inativos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios inativos encontrados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> getAllInactive(){

        List<UsuarioResponseDTO> listaUsuario = usuarioService.buscarTodosUsuariosInativos();

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
    }

    @PostMapping("/cadastrar")
    @Operation(
        summary = "Adicionar",
        description = "Esta requisição adiciona um usuário"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario Criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição")

    })
    public ResponseEntity<UsuarioResponseDTO> postOne(@RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Atualizar por id",
        description = "Esta requisição atualiza o usuário por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Problema com a requisição"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante um processo de requisição"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")

    })
    public ResponseEntity<UsuarioResponseDTO> putOne(@PathVariable long id, @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioAlterado = usuarioService.alterarUsuario(id, usuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAlterado);
    }

    @PutMapping("/desativar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Desativa por id",
        description = "Esta requisição desativa o usuários por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<?> inativeOne(@PathVariable long id) {
        usuarioService.inativarUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PutMapping("/ativar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "Ativa por id",
        description = "Esta requisição ativa o usuário por id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição"),
        @ApiResponse(responseCode = "401", description = "O usuário não esta autenticado"),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização")
    })
    public ResponseEntity<?> activeOne(@PathVariable long id) {
        usuarioService.retivarUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Faz o login de usuário"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario logado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Usuario ou senha incorretos"),
        @ApiResponse(responseCode = "500", description = "Um problema ocorreu durante o processamento da requisição")
    })
    public ResponseEntity<UsuarioLoginResponseDTO> logar(@RequestBody UsuarioLoginRequestDTO usuariologinRequest){
        
        UsuarioLoginResponseDTO usuarioLogado = usuarioService.logar(usuariologinRequest.getEmail(), usuariologinRequest.getSenha());
        
        return ResponseEntity
            .status(200)
            .body(usuarioLogado);
    }
}
