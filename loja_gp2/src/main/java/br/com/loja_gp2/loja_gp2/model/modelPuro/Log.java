package br.com.loja_gp2.loja_gp2.model.modelPuro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoAlteracaoLog;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoAlteracaoLog tipoAlter;
    @Column(nullable = false)
    private Date dataAlter;
    @Column(nullable = false, length = 1000)
    private String valorOriginal;
    @Column(nullable = false, length = 1000)
    private String valorAtual;
    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;
    
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
    public String getValorOriginal() {
        return valorOriginal;
    }
    public void setValorOriginal(String valorOriginal) {
        this.valorOriginal = valorOriginal;
    }
    public String getValorAtual() {
        return valorAtual;
    }
    public void setValorAtual(String valorAtual) {
        this.valorAtual = valorAtual;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }   
}
