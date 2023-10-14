package br.com.loja_gp2.loja_gp2.model.modelPuro;

import java.util.Date;


public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Date dataCadastro;
    private boolean status;
    private Enum perfil;
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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public Enum getPerfil() {
        return perfil;
    }
    public void setPerfil(Enum perfil) {
        this.perfil = perfil;
    }
    
    
}
