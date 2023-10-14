package br.com.loja_gp2.loja_gp2.dto.UsuarioDTO;

public class UsuarioRequestDTO extends UsuarioBaseDTO {
    private String senha;
    private String telefone;
    private Enum perfil;

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

    public Enum getPerfil() {
        return perfil;
    }

    public void setPerfil(Enum perfil) {
        this.perfil = perfil;
    }
    
}
