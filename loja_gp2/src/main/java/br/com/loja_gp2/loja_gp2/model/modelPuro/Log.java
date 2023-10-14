package br.com.loja_gp2.loja_gp2.model.modelPuro;

import java.util.Date;

public class Log {
    private long id;
    private String tipoAlter;
    private Date dataAlter;
    private double valorOriginal;
    private double valorAtual;
    private Usuario usuario;
   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoAlter() {
        return tipoAlter;
    }

    public void setTipoAlter(String tipoAlter) {
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
