package br.com.loja_gp2.loja_gp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Categoria;
import java.util.List;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    List<Categoria> findAllByStatus(boolean status);
}
    