package br.com.loja_gp2.loja_gp2.dto.PedidoDTO;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;

public class PedidoRequestDTO {
    private Usuario usuario;
    private String observacao;
    private Enum formaPagamento;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Enum getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(Enum formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
