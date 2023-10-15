package br.com.loja_gp2.loja_gp2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public void inativarUsuario(long id) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        usuarioEncontrado.get().setStatus(false);

        usuarioRepository.save(usuarioEncontrado.get());   
    }

    public void retivarUsuario(long id) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, "usuario");
        }

        usuarioEncontrado.get().setStatus(true);

        usuarioRepository.save(usuarioEncontrado.get());   
    }

}
