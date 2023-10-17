package br.com.loja_gp2.loja_gp2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;
import br.com.loja_gp2.loja_gp2.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProdutoResponseDTO> buscarTodosProdutos() {
        
        List<Produto> listaProduto = produtoRepository.findAll();

        List<ProdutoResponseDTO> listaProdutoResponse = listaProduto.stream()
        .map(p -> modelMapper.map(p, ProdutoResponseDTO.class)).collect(Collectors.toList());
        
        return listaProdutoResponse;
    }

   public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        if(produtoEncontrado.isEmpty()){
            throw new ResourceNotFoundException(id, "produto");
        }

        return modelMapper.map(produtoEncontrado.get(), ProdutoResponseDTO.class);
   }

   public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequest){

    Produto produto = modelMapper.map(produtoRequest, Produto.class);
    produto.setId(0);

    produto.setStatus(true);

    try{
        if(produto.getEstoque() < 0) {
            throw new ResourceBadRequestException("Produto", "Verifique o campo estoque");
        }
        produto = produtoRepository.save(produto);
    } catch (Exception e) {
        throw new ResourceBadRequestException("nao foi possivel cadastrar o produto"); 
    }
    return modelMapper.map(produto, ProdutoResponseDTO.class);
   }

   public ProdutoResponseDTO atualizarProduto(ProdutoRequestDTO produtoRequest, Long id){
    
    Produto produto = modelMapper.map(produtoRequest, Produto.class);
    buscarProdutoPorId(id);

    produto.setId(id);

    try {
        if(produto.getEstoque() < 0){
            throw new ResourceBadRequestException("Produto", "Verifique o campo estoque");
        }
        produto = produtoRepository.save(produto);
    } catch (Exception e) {
        throw new ResourceBadRequestException("nao foi possivel cadastrar o produto");
    }

    return modelMapper.map(produto, ProdutoResponseDTO.class);
    
    }

   public void inativarProduto(Long id) {

    Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

    if(produtoEncontrado.isEmpty()) {
        throw new ResourceNotFoundException(id, "produto");
    }
    produtoEncontrado.get().setStatus(false);

    produtoRepository.save(produtoEncontrado.get());
   }
   public void reativarProduto(Long id) {

    Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

    if (produtoEncontrado.isEmpty()) {
        throw new ResourceNotFoundException(id, "categoria");
    }

    produtoEncontrado.get().setStatus(true);

    produtoRepository.save(produtoEncontrado.get());
    }

    public long verificarEstoque(long id){
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        if(produtoEncontrado.isEmpty()){
            throw new ResourceNotFoundException(id, "produto");
        }

        return produtoEncontrado.get().getEstoque();
    }

    public ProdutoResponseDTO retirarEstoque(long id, long quantidade) {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        if(produtoEncontrado.isEmpty()){
            throw new ResourceNotFoundException(id, "produto");
        }

        if(quantidade > produtoEncontrado.get().getEstoque()) {
            throw new ResourceBadRequestException("Produto", "Verifique o campo estoque");
        }

        Produto produto = produtoEncontrado.get();
        produto.setEstoque(produto.getEstoque()-quantidade);

        try{
            produto = produtoRepository.save(produto);

        } catch(Exception e) {

        }

        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }
}