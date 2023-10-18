package br.com.loja_gp2.loja_gp2.model.modelPuro;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceBadRequestException;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpedido", nullable = false)
    @JsonBackReference
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "idproduto", nullable = false)
    @JsonBackReference
    private Produto produto;
    @Column(nullable = false)
    private long quantidade;
    @Column(nullable = false)
    private double desconto;
    @Column(nullable = false)
    private double acrescimo;
    @Column(nullable = false)
    private double valorTotal;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
    public double getAcrescimo() {
        return acrescimo;
    }
    public void setAcrescimo(double acrescimo) {
        this.acrescimo = acrescimo;
    }
    public double getValorTotal() {
        return valorTotal;
    }

    private void verificarNegativos () {
        if  (this.acrescimo < 0 || this.desconto < 0 || this.quantidade < 0) {
            throw new ResourceBadRequestException("Acrescimo, desconto ou quantidade não podem ser negativos");
        }  
    }

    private void verificarDesconto() {
        if(this.desconto >= this.valorTotal) {
            this.desconto = this.produto.getValor();
        }   
    }

    public void calcularValorTotal () {
        try {

            verificarNegativos();
            verificarDesconto();

            if(this.acrescimo < 0) this.acrescimo = 0;
            if(this.desconto < 0) this.desconto = 0;

            this.valorTotal = (this.produto.getValor() + this.acrescimo - this.desconto ) * this.quantidade;


        } catch (Exception e) {
            throw new ResourceInternalServerErrorException();
        }
    }
}
