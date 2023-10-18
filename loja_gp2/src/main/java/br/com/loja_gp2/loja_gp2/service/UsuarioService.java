package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.common.ObjetoToJson;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper modelMapper;


    public List<UsuarioResponseDTO> buscarTodosUsuario() {

        List<Usuario> listaUsuario = usuarioRepository.findAll();

        List<UsuarioResponseDTO> listaUsuarioResponse  = listaUsuario.stream()
        .map(e-> modelMapper.map(e, UsuarioResponseDTO.class))
        .collect(Collectors.toList());


        return listaUsuarioResponse;
    }

    public UsuarioResponseDTO buscarUsuarioPorId(long id) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        return modelMapper.map(usuarioEncontrado.get(), UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequest) {

        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        usuario.setId(0);

        usuario.setDataCadastro(new Date());
        usuario.setStatus(true);
        
        try {
            usuario = usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw new ResourceBadRequestException("usuario", "Não foi possivel cadastrar");
        }

        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }
    
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


        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setId(1l);
        
        try {
            BeanUtils.copyProperties(usuarioEncontrado.get(), usuarioOriginal);
            BeanUtils.copyProperties(alteracaoUsuario, usuarioAlterado, "status","dataCadastro","listaLog","listaPedido");
            
            usuarioAlterado = usuarioRepository.save(usuarioAlterado); //

            logService.registrarLog(new Log(
                Usuario.class.getSimpleName(), 
                EnumTipoAlteracaoLog.UPDATE, 
                ObjetoToJson.conversor(usuarioOriginal), 
                ObjetoToJson.conversor(usuarioAlterado),
                usuarioDummy));

        } catch (Exception e) {//
            throw new ResourceBadRequestException("usuario","Não foi possivel alterar");
        }

        return modelMapper.map(usuarioAlterado, UsuarioResponseDTO.class);//
    }

    public void inativarUsuario(long id) {
        mudarStatusUsuario(id, false);
    }

    public void retivarUsuario(long id) {
        mudarStatusUsuario(id, true);
    }

    // vai mudar o sistema de usuario que fez a modificação provavelmente, então seria necessario mais um parametro aqui
    @Transactional
    private void mudarStatusUsuario(long id, boolean status) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        Usuario usuarioOriginal = new Usuario();
        Usuario usuarioAlterado = usuarioEncontrado.get();

        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setId(1l);
        
        try {
            BeanUtils.copyProperties(usuarioEncontrado.get(), usuarioOriginal);
            
            usuarioAlterado.setStatus(status);
            
            usuarioRepository.save(usuarioAlterado);   
            
            logService.registrarLog(new Log(
                Usuario.class.getSimpleName(), 
                EnumTipoAlteracaoLog.UPDATE, 
                ObjetoToJson.conversor(usuarioOriginal), 
                ObjetoToJson.conversor(usuarioAlterado), 
                usuarioDummy));
        } catch (Exception e) {
            throw new ResourceBadRequestException();
        }
    }
}
