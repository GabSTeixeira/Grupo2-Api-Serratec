package br.com.loja_gp2.loja_gp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
    