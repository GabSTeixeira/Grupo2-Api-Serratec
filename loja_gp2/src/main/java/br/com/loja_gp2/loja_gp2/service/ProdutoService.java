package br.com.loja_gp2.loja_gp2.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.loja_gp2.loja_gp2.common.ObjetoToJson;
import br.com.loja_gp2.loja_gp2.dto.CategoriaDTO.CategoriaResponseDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoRequestDTO;
import br.com.loja_gp2.loja_gp2.dto.ProdutoDTO.ProdutoResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPerfil;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceNotFoundException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Categoria;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Log;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;

import br.com.loja_gp2.loja_gp2.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
  
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper modelMapper;

    final String PATH_PASTA_IMAGEM = Paths.get("").toAbsolutePath().toString().concat("\\files\\images\\");

    /**
     * Delega uma busca de uma lista com todos os produtos dentro do banco de dados para o Repository.
     * @return Uma lista de ProdutoResponseDTO
     */
    public List<ProdutoResponseDTO> buscarTodosProdutos() {

        List<Produto> listaProduto = produtoRepository.findAll();

        List<ProdutoResponseDTO> listaProdutoResponse = listaProduto.stream()
                .map(p -> modelMapper.map(p, ProdutoResponseDTO.class)).collect(Collectors.toList());

        return listaProdutoResponse;
    }

    /**
     * Delega uma busca de um produto por id no banco de dados para o Repository.
     * @param id
     * @return Um ProdutoResponseDTO
     */
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        
        if (produtoEncontrado.isEmpty() || produtoEncontrado.get().isStatus() == false) {
            throw new ResourceNotFoundException(id, "produto");
        }

        return modelMapper.map(produtoEncontrado.get(), ProdutoResponseDTO.class);
    }

    /**
     * Delega uma busca de todos os produtos ativos no bando de dados para o Repository.
     * @return Uma lista de ProdutoResponseDTO
     */
    public List<ProdutoResponseDTO> buscarTodasProdutosAtivos(){
        List<Produto> produtosAtivos = produtoRepository.findAllByStatus(true);
        
        List<ProdutoResponseDTO> listaProdutoResponse = produtosAtivos.stream()
        .map(c -> modelMapper.map(c, ProdutoResponseDTO.class)).collect(Collectors.toList());
        
        return listaProdutoResponse;
    }

    /**
     * Delega uma busca de todos os produtos inativos no bando de dados para o Repository.
     * @return Uma lista de ProdutoResponseDTO
     */
    public List<ProdutoResponseDTO> buscarTodasProdutosInativos(){
        List<Produto> produtosInativos = produtoRepository.findAllByStatus(false);
            
        List<ProdutoResponseDTO> listaProdutoResponse = produtosInativos.stream()
        .map(c -> modelMapper.map(c, ProdutoResponseDTO.class)).collect(Collectors.toList());
            
        return listaProdutoResponse;
    }

    /**
     * Delega uma busca de todos os produtos por categoria no banco de dados para o Repository.
     * @param id
     * @return Uma lista de ProdutoResponseDTO
     */
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(Long id) {
        CategoriaResponseDTO categoriaEncontrada = categoriaService.buscarCategoriaPorId(id);
        
        Categoria categoria = modelMapper.map(categoriaEncontrada, Categoria.class);
           
        List<Produto> produtosEncontrados = produtoRepository.findAllByCategoriaAndStatusTrue(categoria);

        List<ProdutoResponseDTO> listaProdutoResponse = produtosEncontrados.stream().map(p -> modelMapper.map(p, ProdutoResponseDTO.class)).collect(Collectors.toList());

        return listaProdutoResponse;
    }

    /**
     * Delega uma busca para de produto para o Repository e baixa a imagem da pasta interna files/images
     * @param id
     * @return uma imagem pertencente ao produto encontrado
     */
    public byte[] downloadImagemProduto (long id) {

        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        Object possivelUsuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        
        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }
        
        // basicamente permite apenas que usuarios logados admin consigam baixar imagem de produtos inativos
        if (produtoEncontrado.get().isStatus() == false) {
            if (!possivelUsuario.equals("anonymousUser")) {
                
                Usuario usuario = (Usuario) possivelUsuario; 
                
                if (usuario.getPerfil().compareTo(EnumTipoPerfil.CLIENTE) == 0) {
                    throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
                }
            } else {
                throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
            }
        }
        

        if (produtoEncontrado.get().getImagemPath() == null) {
            throw new ResourceNotFoundException("Não foi possivel encontrar a imagem deste produto");
        }

        byte[] imagem;

        try {
            imagem = Files.readAllBytes(new File(PATH_PASTA_IMAGEM+produtoEncontrado.get().getImagemPath()).toPath());

        } catch (Exception e) {
            throw new ResourceInternalServerErrorException("Um problema ocorreu durante o download da imagem");
        }

        return imagem;
    }


    /**
     * Delega a ação de registar um path de uma imagem para o Repository e guarda esta imagem na pasta files/images, assim registrado a imagem de um produto
     * @param id
     * @param imagem
     */
    @Transactional
    public void uploadImagemProduto(long id, MultipartFile imagem) {
        
        // obriga a passar o arquivo do tipo png ou jpeg
        if (!imagem.getContentType().equalsIgnoreCase("image/png") && 
            !imagem.getContentType().equalsIgnoreCase("image/jpeg")) {

            throw new ResourceBadRequestException("Por favor mande uma imagem do tipo jpeg ou png");
        }


        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }
        
        String imagemOriginal = produtoEncontrado.get().getImagemPath();
        String novoNomeImagem;
        
        // .jpg .png etc
        String imagemType = imagem.getOriginalFilename().substring(imagem.getOriginalFilename().lastIndexOf('.'));
        

        // se tiver registro de imagem no banco e de fato a imagem existir faça
        if (imagemOriginal != null && new File(PATH_PASTA_IMAGEM+imagemOriginal).exists()) {
            
            // serial unico para o produto
            int serial = Integer.parseInt(imagemOriginal.substring(0, imagemOriginal.indexOf("i")));
            
            // montagem do novo nome da imagem serial+imagemProduto+id+nomeProduto+tipoImagem
            novoNomeImagem = (serial+1) + "imagemProduto" + produtoEncontrado.get().getId() + produtoEncontrado.get().getNome()+imagemType;
        } else {
            novoNomeImagem = "1imagemProduto" + produtoEncontrado.get().getId() + produtoEncontrado.get().getNome()+imagemType;
        }

        // montagem do path inteiro da imagem
        String PATH_ARQUIVO_IMAGEM = PATH_PASTA_IMAGEM + novoNomeImagem;
           
        Produto produtoOriginal = new Produto();
        Produto produtoAlterado = produtoEncontrado.get();
        
        try {
            BeanUtils.copyProperties(produtoEncontrado.get(), produtoOriginal);
            produtoAlterado.setImagemPath(novoNomeImagem);

            byte[] bytes = imagem.getBytes();
            
            // cria um novo arquivo com o nome correto e com o conteudo do multiPartFile
            Files.write(Paths.get(PATH_ARQUIVO_IMAGEM), bytes, StandardOpenOption.CREATE_NEW);
            produtoAlterado = produtoRepository.save(produtoAlterado);
            
            // deleta a imagem antiga
            if (new File(PATH_PASTA_IMAGEM+imagemOriginal).exists()) {        
                new File(PATH_PASTA_IMAGEM+imagemOriginal).delete();
            }
            
        } catch (IOException e) {
            throw new ResourceInternalServerErrorException("Não foi possivel cadastrar a imagem ao produto");
        }
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        logService.registrarLog(new Log(
            Produto.class.getSimpleName(),
            EnumTipoAlteracaoLog.UPDATE,
            ObjetoToJson.conversor(produtoOriginal),
            ObjetoToJson.conversor(produtoAlterado),
            usuario));
    }   

    /**
     * Delega um cadastro de um produto no banco de dados para o Repository.
     * @param produtoRequest
     * @return Uma ProdutoResponseDTO
     */
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequest) {

        Produto produto = modelMapper.map(produtoRequest, Produto.class);
        produto.setId(0);

        produto.setStatus(true);
        
        CategoriaResponseDTO categoriaResponse;
        
        categoriaResponse = categoriaService.buscarCategoriaPorId(produto.getCategoria().getId());
        
        if(categoriaResponse.isStatus() == false) {
            throw new ResourceBadRequestException("Categoria", "Categoria não esta disponivel para o produto");
        }
        
        if (produto.getEstoque() < 0) {
            throw new ResourceBadRequestException("Produto", "Verifique o campo estoque");
        }

      Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            produto = produtoRepository.save(produto);    

        } catch (Exception p) {
            throw new ResourceBadRequestException("nao foi possivel cadastrar o produto");
        }

        logService.registrarLog(new Log(
                    Produto.class.getSimpleName(),
                    EnumTipoAlteracaoLog.CREATE,
                    ObjetoToJson.conversor(produto),
                    ObjetoToJson.conversor(produto),
                    usuario));

        ProdutoResponseDTO produtoResponse = modelMapper.map(produto, ProdutoResponseDTO.class);
        produtoResponse.setCategoria(categoriaResponse);
        return produtoResponse;
    }

    /**
     * Delega a alteração de um produto no banco de dados para o Repository.
     * @param id
     * @param produtoRequest
     * @return Uma ProdutoResponseDTO
     */
    @Transactional
    public ProdutoResponseDTO alterarProduto(long id, ProdutoRequestDTO produtoRequest) {

        Produto produto = modelMapper.map(produtoRequest, Produto.class);
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);
        
        CategoriaResponseDTO categoriaResponse;
        
        categoriaResponse = categoriaService.buscarCategoriaPorId(produto.getCategoria().getId());


        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }
        
        if (produto.getEstoque() < 0) {
            throw new ResourceBadRequestException(Produto.class.getSimpleName(), "Verifique o campo estoque");
        }

        if(categoriaResponse.isStatus() == false) {
            throw new ResourceBadRequestException(Produto.class.getSimpleName(), "Categoria, ele pode não estar disponivel");
        }
        
        
        produto.setStatus(produtoEncontrado.get().isStatus());
        produto.setId(id);
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            produto = produtoRepository.save(produto);
        } catch (Exception e) {
            throw new ResourceBadRequestException("Nao foi possivel cadastrar o produto");
        }

        logService.registrarLog(new Log(
                    Produto.class.getSimpleName(),
                    EnumTipoAlteracaoLog.UPDATE,
                    ObjetoToJson.conversor(produtoEncontrado.get()),
                    ObjetoToJson.conversor(produto),
                    usuario));

        ProdutoResponseDTO produtoResponse = modelMapper.map(produto, ProdutoResponseDTO.class);
        
        produtoResponse.setCategoria(categoriaResponse);
        
        return produtoResponse;

    }

    /**
     * Delega uma inativação de um produto no banco de dados para o Repository.
     * @param id
     */
    @Transactional
    public void inativarProduto(Long id) {

        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        if(produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Categoria.class.getSimpleName());
        }

        
        Produto produtoOriginal = new Produto();
        Produto produtoAlterado = produtoEncontrado.get();
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            BeanUtils.copyProperties(produtoEncontrado.get(), produtoOriginal);
            produtoAlterado.setStatus(false);

            produtoAlterado = produtoRepository.save(produtoAlterado);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }

        logService.registrarLog(new Log(
                Produto.class.getSimpleName(), 
                EnumTipoAlteracaoLog.UPDATE, 
                ObjetoToJson.conversor(produtoOriginal),
                ObjetoToJson.conversor(produtoAlterado),
                usuario));
    }

    /**
     * Delega uma reativação de um produto no banco de dados para o Repository.
     * @param id
     */
    @Transactional
    public void reativarProduto(Long id) {

         Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        if(produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }
        
        Produto produtoOriginal = new Produto();
        Produto produtoAlterado = produtoEncontrado.get();
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            BeanUtils.copyProperties(produtoEncontrado.get(), produtoOriginal);
            produtoAlterado.setStatus(true);
            
            produtoAlterado = produtoRepository.save(produtoAlterado);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }
        
        logService.registrarLog(new Log(
                Produto.class.getSimpleName(), 
                EnumTipoAlteracaoLog.UPDATE, 
                ObjetoToJson.conversor(produtoOriginal), 
                ObjetoToJson.conversor(produtoAlterado), 
                usuario));
    }

    /**
     * Verifica a quantidade em estoque de um produto no banco de dados trazido pelo ProdutoRepository.
     * @param id
     * @return Um Long com a quantidade em estoque do produto
     */
    public long verificarEstoque(long id) {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }

        return produtoEncontrado.get().getEstoque();
    }

    /**
     * Retira uma quantidade do estoque de um produto no banco de dados trazido pelo ProdutoRepository.
     * @param id
     * @param quantidade
     * @return
     */
    public ProdutoResponseDTO retirarEstoque(long id, long quantidade) {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException(id, Produto.class.getSimpleName());
        }

        if (quantidade > produtoEncontrado.get().getEstoque()) {
            throw new ResourceBadRequestException(Produto.class.getSimpleName(), "Verifique o campo estoque");
        }

        Produto produto = produtoEncontrado.get();
        produto.setEstoque(produto.getEstoque() - quantidade);

        try {
            produto = produtoRepository.save(produto);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }



        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }
}