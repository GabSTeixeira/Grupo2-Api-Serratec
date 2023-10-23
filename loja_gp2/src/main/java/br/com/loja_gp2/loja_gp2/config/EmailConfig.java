package br.com.loja_gp2.loja_gp2.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:env/mail.properties")
public class EmailConfig {
    
    @Autowired
    private Environment env;
    
    /**
     * Método feito para que o Spring enxergue o JavaMailSenderImpl sem a necessidade de instanciá-lo.
     * @return Um objeto instanciado do tipo JavaMailSenderImpl
     */
    @Bean
    public JavaMailSender mailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("mail.smtp.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.smtp.port")));
        mailSender.setUsername(env.getProperty("mail.smtp.username"));
        mailSender.setPassword(env.getProperty("mail.smtp.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol",  "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug","true");

        return mailSender;
    }
}