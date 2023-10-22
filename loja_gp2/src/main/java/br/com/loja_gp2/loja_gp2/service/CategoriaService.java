package br.com.loja_gp2.loja_gp2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.common.ObjetoToJson;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Categoria;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import br.com.loja_gp2.loja_gp2.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LogService logService;

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
        
        if(categoriaEncontrada.isEmpty() || categoriaEncontrada.get().isStatus() == false){
            throw new ResourceNotFoundException(id, "categoria");
        }

        return modelMapper.map(categoriaEncontrada.get(), CategoriaResponseDTO.class);
    }

    public List<CategoriaResponseDTO> buscarTodasCategoriasAtivas(){
        List<Categoria> categoriasAtivas = categoriaRepository.findAllByStatus(true);
        
        List<CategoriaResponseDTO> listaCategoriaResponse = categoriasAtivas.stream()
        .map(c -> modelMapper.map(c, CategoriaResponseDTO.class)).collect(Collectors.toList());
        
        return listaCategoriaResponse;
    }

    public List<CategoriaResponseDTO> buscarTodasCategoriasInativas(){
        List<Categoria> categoriasInativas = categoriaRepository.findAllByStatus(false);
        
        List<CategoriaResponseDTO> listaCategoriaResponse = categoriasInativas.stream()
        .map(c -> modelMapper.map(c, CategoriaResponseDTO.class)).collect(Collectors.toList());
        
        return listaCategoriaResponse;
    }

    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaRequest){

        Categoria categoria = modelMapper.map(categoriaRequest, Categoria.class);
        categoria.setId(0);

        categoria.setStatus(true);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            categoria = categoriaRepository.save(categoria);
        } catch (Exception e) {
            throw new ResourceBadRequestException("categoria", "Não foi possível cadastrar");
        }

        logService.registrarLog(new Log(
                    Produto.class.getSimpleName(),
                    EnumTipoAlteracaoLog.UPDATE,
                    ObjetoToJson.conversor(categoria),
                    ObjetoToJson.conversor(categoria),
                    usuario));

        return modelMapper.map(categoria, CategoriaResponseDTO.class);
    }

    public CategoriaResponseDTO alterarCategoria(long id, CategoriaRequestDTO categoriaRequest) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }

        Categoria alteracaoCategoria = modelMapper.map(categoriaRequest, Categoria.class);
        alteracaoCategoria.setId(id);
        alteracaoCategoria.setStatus(categoriaEncontrada.get().isStatus());

        Categoria categoriaOriginal = new Categoria();
        Categoria categoriaAlterada;

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            BeanUtils.copyProperties(categoriaEncontrada.get(), categoriaOriginal);

            categoriaAlterada = categoriaRepository.save(alteracaoCategoria);

        }
        catch (Exception e) {
            throw new ResourceBadRequestException("categoria","Não foi possivel alterar a categoria");
        }

        logService.registrarLog(new Log(
            Categoria.class.getSimpleName(),
            EnumTipoAlteracaoLog.UPDATE,
            ObjetoToJson.conversor(categoriaOriginal),
            ObjetoToJson.conversor(categoriaAlterada),
            usuario));
        
        return modelMapper.map(categoriaAlterada, CategoriaResponseDTO.class);//
        
    }
    
    public void inativarCategoria(Long id) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if(categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }

        
        Categoria categoriaOriginal = new Categoria();
        Categoria categoriaAlterada = categoriaEncontrada.get();
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            BeanUtils.copyProperties(categoriaEncontrada.get(), categoriaOriginal);
            categoriaAlterada.setStatus(false);
            
            categoriaAlterada = categoriaRepository.save(categoriaAlterada);

            
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }

        logService.registrarLog(new Log(
            Categoria.class.getSimpleName(), 
            EnumTipoAlteracaoLog.UPDATE, 
            ObjetoToJson.conversor(categoriaOriginal), 
            ObjetoToJson.conversor(categoriaAlterada), 
            usuario));
    }

    public void reativarCategoria(Long id) {

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new ResourceNotFoundException(id, "categoria");
        }
        Categoria categoriaOriginal = new Categoria();
        Categoria categoriaAlterada = categoriaEncontrada.get();

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            BeanUtils.copyProperties(categoriaEncontrada.get(), categoriaOriginal);
            
            categoriaAlterada.setStatus(true);
            categoriaAlterada = categoriaRepository.save(categoriaAlterada);
            

        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }
        
        logService.registrarLog(new Log(
                Categoria.class.getSimpleName(),
                EnumTipoAlteracaoLog.UPDATE,
                ObjetoToJson.conversor(categoriaOriginal),
                ObjetoToJson.conversor(categoriaAlterada),
                usuario));
    }
}
