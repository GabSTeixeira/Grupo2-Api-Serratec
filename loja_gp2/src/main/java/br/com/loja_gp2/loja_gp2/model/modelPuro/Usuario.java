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
import javax.persistence.OneToMany;

import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPerfil;

@Entity
public class Usuario {
    
    //#region propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private Date dataCadastro;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoPerfil perfil;
    @Column(nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "usuario")
    private List<Pedido> listaPedido;
    @OneToMany(mappedBy = "usuario")
    private transient List<Log> listaLog;
    // transient é para as conversões objetos em json funcionar sem entrar em loop

    //#endregion propriedades
    
    //#region getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public EnumTipoPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumTipoPerfil perfil) {
        this.perfil = perfil;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Pedido> getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(List<Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }

    public List<Log> getListaLog() {
        return listaLog;
    }

    public void setListaLog(List<Log> listaLog) {
        this.listaLog = listaLog;
    }
    //#endregion getters and setters
}
