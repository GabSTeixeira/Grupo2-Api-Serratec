package br.com.loja_gp2.loja_gp2.dto.UsuarioDTO;

import br.com.loja_gp2.loja_gp2.model.Enum.EnumTipoPerfil;

public class UsuarioRequestDTO extends UsuarioBaseDTO {
    private String senha;
    private String telefone;
    private EnumTipoPerfil perfil;

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

    public EnumTipoPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumTipoPerfil perfil) {
        this.perfil = perfil;
    } 
}
