package br.com.loja_gp2.loja_gp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Categoria;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Produto;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
    List<Produto> findAllByCategoriaAndStatusTrue(Categoria categoria);
    List<Produto> findAllByStatus(boolean status);
}
    
    

