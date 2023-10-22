package br.com.loja_gp2.loja_gp2.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.common.ObjetoToJson;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioLoginResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPerfil;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.UsuarioRepository;
import br.com.loja_gp2.loja_gp2.security.JWTService;

@Service
public class UsuarioService {
    private static final String BEARER = "Bearer ";
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LogService logService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Delega uma busca de uma lista com todos os usuários dentro do banco de dados para o Repository.
     * @return Uma lista de UsuarioResponseDTO
     */
    public List<UsuarioResponseDTO> buscarTodosUsuario() {

        List<Usuario> listaUsuario = usuarioRepository.findAll();

        List<UsuarioResponseDTO> listaUsuarioResponse  = listaUsuario.stream()
        .map(e-> modelMapper.map(e, UsuarioResponseDTO.class))
        .collect(Collectors.toList());


        return listaUsuarioResponse;
    }

    /**
     * Delega uma busca de um usuário por id no banco de dados para o Repository.
     * @param id
     * @return Um UsuarioResponseDTO
     */
    public UsuarioResponseDTO buscarUsuarioPorId(long id) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioEncontrado.isEmpty() || (usuario.getPerfil().compareTo(EnumTipoPerfil.CLIENTE) == 0 && usuarioEncontrado.get().getId() != usuario.getId())) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        return modelMapper.map(usuarioEncontrado.get(), UsuarioResponseDTO.class);
    }

    /**
     * Delega uma busca de todos os usuários ativos no bando de dados para o Repository.
     * @return Uma lista de UsuarioResponseDTO
     */
    public List<UsuarioResponseDTO> buscarTodosUsuariosAtivos(){

        List<Usuario> listaUsuariosAtivos = usuarioRepository.findAllByStatus(true);

        List<UsuarioResponseDTO> listaUsuarioResponse = listaUsuariosAtivos.stream()
        .map(u -> modelMapper.map(u, UsuarioResponseDTO.class)).collect(Collectors.toList());

        return listaUsuarioResponse;
    }

    /**
     * Delega uma busca de todos os usuários inativos no bando de dados para o Repository.
     * @return Uma lista de UsuarioResponseDTO
     */
    public List<UsuarioResponseDTO> buscarTodosUsuariosInativos(){

        List<Usuario> listaUsuariosInativos = usuarioRepository.findAllByStatus(false);

        List<UsuarioResponseDTO> listaUsuarioResponse = listaUsuariosInativos.stream()
        .map(u -> modelMapper.map(u, UsuarioResponseDTO.class)).collect(Collectors.toList());
        
        return listaUsuarioResponse;
    }

    /**
     * Delega um cadastro de um usuário no banco de dados para o Repository.
     * @param usuarioRequest
     * @return Uma UsuarioResponseDTO
     */
    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequest) {

        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        usuario.setId(0);

        usuario.setDataCadastro(new Date());
        usuario.setStatus(true);
        
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

            usuario = usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw new ResourceBadRequestException("usuario", "Não foi possivel cadastrar");
        }

        emailService.criarEmailCadastro(usuario);

        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }
    
    /**
     * Delega a alteração de um usuario no banco de dados para o Repository.
     * @param id
     * @param usuarioRequest
     * @return Uma UsuarioResponseDTO
     */
    @Transactional
    public UsuarioResponseDTO alterarUsuario(long id, UsuarioRequestDTO usuarioRequest) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        Usuario alteracaoUsuario = modelMapper.map(usuarioRequest, Usuario.class);
        alteracaoUsuario.setId(id);

        Usuario usuarioOriginal = new Usuario();
        Usuario usuarioAlterado = usuarioEncontrado.get();


        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            BeanUtils.copyProperties(usuarioEncontrado.get(), usuarioOriginal);
            BeanUtils.copyProperties(alteracaoUsuario, usuarioAlterado, "status","dataCadastro","listaLog","listaPedido");
            
            usuarioAlterado.setSenha(passwordEncoder.encode(usuarioAlterado.getSenha()));

            usuarioAlterado = usuarioRepository.save(usuarioAlterado); //

            
        } catch (Exception e) {//
            throw new ResourceBadRequestException("usuario","Não foi possivel alterar");
        }
        
        logService.registrarLog(new Log(
            Usuario.class.getSimpleName(), 
            EnumTipoAlteracaoLog.UPDATE, 
            ObjetoToJson.conversor(usuarioOriginal), 
            ObjetoToJson.conversor(usuarioAlterado),
            usuario));

        return modelMapper.map(usuarioAlterado, UsuarioResponseDTO.class);//
    }

    /**
     * Recebe e passa um id e um status false pro método MudasStatusUsuario.
     * @param id
     */
    public void inativarUsuario(long id) {
        mudarStatusUsuario(id, false);
    }

    /**
     * Recebe e passa um id e um status true pro método MudasStatusUsuario.
     * @param id
     */
    public void retivarUsuario(long id) {
        mudarStatusUsuario(id, true);
    }

    /**
     * Delega uma ativação ou inativação de um usuário no banco de dados para o Repository.
     * @param id
     * @param status
     */
    @Transactional
    private void mudarStatusUsuario(long id, boolean status) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        Usuario usuarioOriginal = new Usuario();
        Usuario usuarioAlterado = usuarioEncontrado.get();

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            BeanUtils.copyProperties(usuarioEncontrado.get(), usuarioOriginal);
            
            usuarioAlterado.setStatus(status);
            
            usuarioRepository.save(usuarioAlterado);   
        } catch (Exception e) {
            throw new ResourceBadRequestException();
        }
        
        logService.registrarLog(new Log(
            Usuario.class.getSimpleName(), 
            EnumTipoAlteracaoLog.UPDATE, 
            ObjetoToJson.conversor(usuarioOriginal), 
            ObjetoToJson.conversor(usuarioAlterado), 
            usuario));
    }

    /**
     * Delega uma busca de um usuário no bando de dados pelo seu email para o Repository.
     * @param email
     * @return Um UsuarioResponseDTO
     */
    public UsuarioResponseDTO obterPorEmail(String email){
        Optional<Usuario> optUsuario =  usuarioRepository.findByEmail(email);

        return modelMapper.map(optUsuario.get(),UsuarioResponseDTO.class);
    }

    /**
     * Realiza o login de um usuário na API.
     * @param email
     * @param senha
     * @return Um UsuarioLoginResponseDTO
     */
    public UsuarioLoginResponseDTO logar(String email, String senha){
        // Aqui que a autenticação acontece dentro do spring automagicamente.
        Authentication autenticacao = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, senha,Collections.emptyList()));
            
        // Aqui eu passo a nova autenticação para o springSecurity cuidar pra mim.
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Crio o token JWT
        String token =  BEARER + jwtService.gerarToken(autenticacao);
    
        // Pego o usuario dono do token
        UsuarioResponseDTO usuarioResponse = obterPorEmail(email);

        // Crio e devolvo o DTO esperado.
        return new UsuarioLoginResponseDTO(token, usuarioResponse);
    }
}
