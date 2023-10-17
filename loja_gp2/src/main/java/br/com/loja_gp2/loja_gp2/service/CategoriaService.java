package br.com.loja_gp2.loja_gp2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Categoria;
import br.com.loja_gp2.loja_gp2.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    public List<CategoriaResponseDTO> buscarTodasCategorias() {

        List<Categoria> listaCategoria = categoriaRepository.findAll();
        
        List<CategoriaResponseDTO> listaCategoriaResponse = listaCategoria.stream()
        .map(c -> modelMapper.map(c, CategoriaResponseDTO.class)).collect(Collectors.toList());

        return listaCategoriaResponse;
    }

    public CategoriaResponseDTO buscarCategoriaPorId(Long id) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);
        
        if(categoriaEncontrada.isEmpty()){
            throw new ResourceNotFoundException(id, "categoria");
        }

        return modelMapper.map(categoriaEncontrada.get(), CategoriaResponseDTO.class);
    }
    @Transactional
    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaRequest){

        Categoria categoria = modelMapper.map(categoriaRequest, Categoria.class);
        categoria.setId(0);

        categoria.setStatus(true);

        try {
            categoria = categoriaRepository.save(categoria);
        } catch (Exception e) {
            throw new ResourceBadRequestException("categoria", "Não foi possível cadastrar");
        }

        return modelMapper.map(categoria, CategoriaResponseDTO.class);
    }

    @Transactional
    public CategoriaResponseDTO alterarCategoria(long id, CategoriaRequestDTO categoriaRequest) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }

        Categoria alteracaoCategoria = modelMapper.map(categoriaRequest, Categoria.class);
        alteracaoCategoria.setId(id);

        Categoria categoriaOriginal = new Categoria();
        Categoria categoriaAlterada = categoriaEncontrada.get();

        
        try {
            categoriaAlterada = categoriaRepository.save(categoriaAlterada);
        }
        catch (Exception e) {//
            throw new ResourceBadRequestException("categoria","Não foi possivel alterar a categoria");
        }

        return modelMapper.map(categoriaAlterada, CategoriaResponseDTO.class);//
        
    }
    
    @Transactional
    public void inativarCategoria(Long id) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if(categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }

        categoriaEncontrada.get().setStatus(false);

        categoriaRepository.save(categoriaEncontrada.get());
    }
    @Transactional
    public void reativarCategoria(Long id) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }

        categoriaEncontrada.get().setStatus(true);

        categoriaRepository.save(categoriaEncontrada.get());
    }
}
