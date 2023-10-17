package br.com.loja_gp2.loja_gp2.dto.LogDTO;

import java.util.Date;

import br.com.loja_gp2.loja_gp2.dto.UsuarioDTO.UsuarioResponseDTO;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;

public class LogResponseDTO {
    private long id;
    private EnumTipoAlteracaoLog tipoAlter;
    private Date dataAlter;
    private double valorOriginal;
    private double valorAtual;;
    private UsuarioResponseDTO usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumTipoAlteracaoLog getTipoAlter() {
        return tipoAlter;
    }

    public void setTipoAlter(EnumTipoAlteracaoLog tipoAlter) {
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

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}
