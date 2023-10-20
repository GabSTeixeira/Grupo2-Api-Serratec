package br.com.loja_gp2.loja_gp2.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.common.ConversorData;
import br.com.loja_gp2.loja_gp2.model.Email.Email;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Item;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Pedido;
import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private void enviar(Email email) {
        
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(email.getRemetente());
            helper.setSubject(email.getAssunto());
            helper.setText(email.getMensagem(), true);  //true para html
            helper.setTo(email.getDestinatarios()
                .toArray(new String[email.getDestinatarios().size()])
            );

            javaMailSender.send(mimeMessage);
            
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException("Ocorreu um erro durante o envio de um email");
        }
    
    
    }

    public void criarEmailPedido(Usuario usuario, Pedido pedido){
        final String DADO = "Pedido";
        final String ASSUNTO = "Confirmação de pedido";

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(usuario.getEmail());

        String mensagem = new String();

        mensagem.concat("<body>");
        
        mensagem.concat("<table border=1>");
        mensagem.concat("<tr><th>Nome</th><th>Quantidade</th><th>Valor</th>");
        
        for (Item item :  pedido.getListaItens()) {     
            mensagem.concat("<tr><td>"+item.getProduto().getNome()+"</td>");
            mensagem.concat("<td>"+item.getQuantidade()+"</td>");
            mensagem.concat("<td>"+item.getValorLiquido()+"</td></tr>");
        }

        mensagem.concat("</table></body>");

        Email email = new Email(ASSUNTO, mensagem, DADO+"@EspacoDosGames.com", destinatarios);

        enviar(email);

        
    }


    public void criarEmailCadastro(Usuario usuario){
        final String DADO = "Cadastro";
        final String ASSUNTO = "Confirmação de cadastro";
        
        final String MENSAGEM = ""
        .concat("<body>")
        .concat("<p style=\"text-align:center\">")
        .concat("<img alt=\"\" src=\"https://i.imgur.com/4E5yT11.png\" style=\"height:275px; width:1200px\" /></p>")
        
        .concat("<h1 style=\"text-align:center\">")
        .concat("<span style=\"color:#000000\"><span style=\"font-family:Times New Roman,Times,serif\">Bem Vindo "+usuario.getNome()+" ao Espa&ccedil;o dos Games!</span></span></h1>")
        .concat("<p style=\"text-align:center\"><span style=\"font-size:16px\">")
        .concat("<span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("Na nossa loja voc&ecirc; encontrar&aacute; os melhores produtos para acrescentar a sua cole&ccedil;&atilde;o e se divertir ao m&aacute;ximo!</span></span></p>")
        
        .concat("<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("N&oacute;s temos desde jogos vintage at&eacute; os mais famosos no momento!</span></span></p>")

        .concat("<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("N&atilde;o perca tempo e aproveite todos os novos descontos imperd&iacute;veis na nossa loja!</span></span></p>")

        .concat("<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("Comece agora uma nova jornada no maravilhoso mundo dos jogos!</span></span></p>")

        .concat("<h2 style=\"text-align:center\"><span style=\"color:#000000\"><span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("Esperamos o seu Pedido!</span></span></h2>")

        .concat("</body>");


        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(usuario.getEmail());

        Email email = new Email(ASSUNTO, MENSAGEM, DADO+"@EspacoDosGames.com", destinatarios);

        enviar(email);    
    }


    
}
