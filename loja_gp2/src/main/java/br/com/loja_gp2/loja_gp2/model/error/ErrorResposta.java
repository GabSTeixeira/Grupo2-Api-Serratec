package br.com.loja_gp2.loja_gp2.model.error;


public class ErrorResposta {
    
    private int status;

    private String titulo;

    private String mensagem;

    private String dataHora;

    public ErrorResposta(int status, String titulo, String mensagem, String dataHora) {
        this.status = status;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    
}
