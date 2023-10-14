package br.com.loja_gp2.loja_gp2.dto.LogDTO;

import java.util.Date;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;

public class LogResponseDTO {
    private long id;
    private Enum tipoAlter;
    private Date dataAlter;
    private double valorOriginal;
    private double valorAtual;;
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Enum getTipoAlter() {
        return tipoAlter;
    }

    public void setTipoAlter(Enum tipoAlter) {
        this.tipoAlter = tipoAlter;
    }

    public Date getDataAlter() {
        return dataAlter;
    }

    public void setDataAlter(Date dataAlter) {
        this.dataAlter = dataAlter;
    }

    public double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
