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

    /**
     * Faz o envio de um email.
     * @param email
     */
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

    /**
     * Prepara o email que será enviado quando um pedido for finalizado. 
     * @param usuario
     * @param pedido
     */
    public void criarEmailPedido(Usuario usuario, Pedido pedido){
        final String DADO = "Pedido";
        final String ASSUNTO = "Confirmação de pedido";

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(usuario.getEmail());

        String MENSAGEM = ""
        .concat("<p style=\"text-align:center\">")
        .concat("<img alt=\"\" src=\"https://i.imgur.com/LPzbZZt.png\" style=\"height:275px; width:1200px\" /></p>")
        .concat("<h1 style=\"text-align:center\">")
        .concat("<span style=\"color:#000000; font-family:Times New Roman,Times,serif\">Obrigado por Comprar Conosco!</span></h1>")
        .concat("<h2 style=\"text-align:center\">")
        .concat("<span style=\"font-family:Times New Roman,Times,serif\">")
        .concat("<span style=\"font-size:16px\">Confira todas as informa&ccedil;&otilde;es do seu pedido a seguir:</span></span></h2>")
        .concat("<table align=\"center\" border=\"1\" cellpadding=\"1\" style=\"width:800px\">")
        .concat("<tbody><tr>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">Produto</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">Quantidade</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">Acrescimo</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">Desconto</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">Valor Bruto</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\"><span style=\"background-color:#ffffff\">Valor Liquido</span></span></td></tr>");
        long totalquantidade = 0; 
        long totalacrescimo = 0;
        long totaldesconto = 0;

        for (Item item :  pedido.getListaItens()) { 
            totalquantidade += item.getQuantidade();
            totalacrescimo += item.getAcrescimo();
            totaldesconto += item.getDesconto();    
            MENSAGEM += "".concat("<tr><td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getProduto().getNome()+"</span></td>")
            .concat("<td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getQuantidade()+"</span></td>")
            .concat("<td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getAcrescimo()+"</span></td>")
            .concat("<td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getDesconto()+"</span></td>")
            .concat("<td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getValorBruto()+"</span></td>")
            .concat("<td><span style=\"font-family:Arial,Helvetica,sans-serif\">"+item.getValorLiquido()+"</span></td></tr>");
        }

        MENSAGEM += "".concat("<tr><td><span style=\"font-family:Times New Roman,Times,serif\">Totais</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">"+totalquantidade+"</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">"+(totalacrescimo+pedido.getAcrescimoPedido())+"</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">"+(totaldesconto+pedido.getDescontoPedido())+"</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">"+pedido.getValorBruto()+"</span></td>")
        .concat("<td><span style=\"font-family:Times New Roman,Times,serif\">"+pedido.getValorLiquido()+"</span></td></tr>")
        .concat("</tbody></table>")
        .concat("<footer><h2 style=\"text-align:center\">")
        .concat("<span style=\"font-family:Times New Roman,Times,serif\">Logo enviaremos o seu pedido, esteja atento a novas informa&ccedil;&otilde;es relacionadas a entrega dos seus produtos!</span></h2></footer>");

        Email email = new Email(ASSUNTO, MENSAGEM, DADO+"@EspacoDosGames.com", destinatarios);

        enviar(email);

        
    }

    /**
     * Prepara o email que será enviado quando um usuário faz um cadastro.
     * @param usuario
     */
    public void criarEmailCadastro(Usuario usuario){
        final String DADO = "Cadastro";
        final String ASSUNTO = "Confirmação de cadastro";
        
        final String MENSAGEM = ""
        .concat("<body>")
        .concat("<p style=\"text-align:center\">")
        .concat("<img alt=\"\" src=\"https://i.imgur.com/LPzbZZt.png\" style=\"height:275px; width:1200px\" /></p>")
        
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
