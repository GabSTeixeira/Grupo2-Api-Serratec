package br.com.loja_gp2.loja_gp2.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.loja_gp2.loja_gp2.common.ConversorData;
import br.com.loja_gp2.loja_gp2.model.Email.Email;
import br.com.loja_gp2.loja_gp2.model.exceptions.ResourceInternalServerErrorException;
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

        String mensagem = "<body style=\"background-color: #000000;\">\r\n" +
        "<p><img alt=\"\" src=\"https://marketplace.canva.com/EAFDIxD2tZY/1/0/1600w/canva-logotipo-para-%C3%A1rea-de-tecnologia-e-games-ilustra%C3%A7%C3%A3o-e-sports-laranja-e-preto-8hlmOHD8SBs.jpg\" style=\"height:250px; margin-left:500px; margin-right:500px; width:250px\" /></p>\r\n"+
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Pedido de número: "+pedido.getId()+"</span></span></span></p>\r\n"+
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Caro "+usuario.getNome()+".</span></span></span></p>\r\n"+
        
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Data: "+ConversorData.converterDateParaDataHora(pedido.getDataPedido())+"<br/>Forma de pagamento: "+pedido.getFormaPagamento()+" </span></span></span></p>\r\n"+
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Valor Total do Pedido: R$"+pedido.getValorTotal()+"</span></span></span></p>\r\n"+
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Obrigado por realizar uma compra em nossa loja.</span></span></span></p>\r\n"+
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Este email serve como confirma&ccedil;ao que sua compra de numero foi cadastrada e agora &eacute; so vc aguardar o envio de nossa loja.</span><br />\r\n"+
        "<span style=\"background-color:black\">Divirta-se!</span></span></span></p>\r\n"+ 
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Atenciosamente,</span><br />\r\n"+
        "<span style=\"background-color:#000000\"> A loja Espa&ccedil;o dos Games</span></span></span></p>\r\n"+
        "</body>";


        Email email = new Email(ASSUNTO, mensagem, DADO+"@EspacoDosGames.com", destinatarios);

        enviar(email);

        
    }


    public void criarEmailCadastro(Usuario usuario){
        final String DADO = "Cadastro";
        final String ASSUNTO = "Confirmação de cadastro";
        
        String mensagem = "<body style=\"background-color: #000000;\">\r\n" +
        "<p><img alt=\"\" src=\"https://marketplace.canva.com/EAFDIxD2tZY/1/0/1600w/canva-logotipo-para-%C3%A1rea-de-tecnologia-e-games-ilustra%C3%A7%C3%A3o-e-sports-laranja-e-preto-8hlmOHD8SBs.jpg\" style=\"height:250px; margin-left:500px; margin-right:500px; width:250px\" /></p>\r\n" +
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Caro "+usuario.getNome()+".</span></span></span></p>\r\n" +
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Obrigado por realizar uma compra em nossa loja.</span></span></span></p>\r\n" +
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Este email serve como confirma&ccedil;ao que sua compra foi cadastrada e agora &eacute; so vc aguardar o envio de nossa loja.</span><br />\r\n" +
        "<span style=\"background-color:black\">Divirta-se!</span></span></span></p>\r\n" +
        "<p><span style=\"color:#ffffff\"><span style=\"font-size:20px\"><span style=\"background-color:#000000\"> Atenciosamente,</span><br />\r\n" +
        "<span style=\"background-color:#000000\"> A loja Espa&ccedil;o dos Games</span></span></span></p>\r\n" +
        "</body>";

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(usuario.getEmail());

        Email email = new Email(ASSUNTO, mensagem, DADO+"@EspacoDosGames.com", destinatarios);

        enviar(email);    
    }


    
}
