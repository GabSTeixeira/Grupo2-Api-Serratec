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

import br.com.loja_gp2.loja_gp2.model.Email.Email;

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
            System.out.println("Deu ruim no envio de e-mail:");
            System.out.println(e.getMessage());
        }
    
    
    }
    @GetMapping("/email")
    public void criarEmail(List<String>destinatarios, String mensagem, String assunto, String dados){
        //email de confirmacao
        //String mensagem = 
        
        Email email = new Email(assunto, mensagem, dados+"@lojaGp2.com", destinatarios);

        enviar(email);

        
    }
    
}
