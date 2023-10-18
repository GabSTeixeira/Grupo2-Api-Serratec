package br.com.loja_gp2.loja_gp2.model.modelPuro;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPagamento;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "idusario", nullable = false)
    @JsonBackReference
    private Usuario usuario;
    @OneToMany(mappedBy = "pedido")
    private List<Item> listaItens;
    @Column(nullable = false)
    private Date dataPedido;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoPagamento formaPagamento;
    @Column(nullable = false)
    private double descontoPedido;
    @Column(nullable = false)
    private double acrescimoPedido;
    @Column(nullable = false)
    private double descontoTotal;
    @Column(nullable = false)
    private double acrescimoTotal;
    @Column(nullable = false)
    private double valorTotal;
    private String observacao;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<Item> getListaItens() {
        return listaItens;
    }
    public void setListaItens(List<Item> listaItens) {
        this.listaItens = listaItens;
    }
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public EnumTipoPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(EnumTipoPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public double getDescontoPedido() {
        return descontoPedido;
    }
    public void setDescontoPedido(double descontoPedido) {
        this.descontoPedido = descontoPedido;
    }
    public double getAcrescimoPedido() {
        return acrescimoPedido;
    }
    public void setAcrescimoPedido(double acrescimoPedido) {
        this.acrescimoPedido = acrescimoPedido;
    }
    public double getDescontoTotal() {
        return descontoTotal;
    }
    public void setDescontoTotal(double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }
    public double getAcrescimoTotal() {
        return acrescimoTotal;
    }
    public void setAcrescimoTotal(double acrescimoTotal) {
        this.acrescimoTotal = acrescimoTotal;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    } 

    public void calcularTotais() {
        for (Item item: this.listaItens) {
            this.acrescimoTotal += item.getAcrescimo();
            this.descontoTotal += item.getDesconto();
            this.valorTotal += item.getValorTotal();
        }

        this.acrescimoTotal += this.acrescimoPedido;
        this.descontoTotal += this.descontoPedido;

        this.valorTotal += this.acrescimoPedido - this.descontoPedido;

    }
}
