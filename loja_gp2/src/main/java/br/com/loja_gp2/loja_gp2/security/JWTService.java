package br.com.loja_gp2.loja_gp2.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.loja_gp2.loja_gp2.model.modelPuro.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTService {
    
    // Chave secreta utilizada pelo jwt para codificar e decodificar o token.
    private static final String SECURITY_KEY = "ChaveMuitoSecreta";

    /**
     * Método para retornar o token JWT.
     * @param authentication Autenticação do usuário
     * @author Weberson Rodrigues
     * @return Token JWT
     */
    public String gerarToken(Authentication authentication){

        // 1 Dia em milliseconds
        // Aqui pode variar de acordo com a regra de negocio
        int tempoExpiracao = 86400000;

        // Isso aqui gera uma data com um dia a mais
        Date dataExpiracao = new Date(new Date().getTime() + tempoExpiracao);

        // Aqui pego o usuario atual da autenticação.
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // retorna o token jwt.
        return Jwts.builder()
                .setSubject(usuario.getId().toString()) // Identificador unico do usuario
                .setIssuedAt(new Date()) //Data da geração do token.
                .setExpiration(dataExpiracao) // Data de expiração do token.
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY) //Algoritimo de criptografia e a chave secreta.
                .compact(); // Aqui pega tudo e gera o token...
    }


    /**
     * Método para retornar o id do usuário dono do token.
     * @param token Token do usuario
     * @return Id do usuario
     */
    public Optional<Long> obterIdDoUsuario(String token){
        try {
            // Aqui pego a claim do token para achar o usuario dono dele.
          Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();  

           // Se achou o id dentro da claim, ele devolve, se não ele devolve null.
          return Optional.ofNullable(Long.parseLong(claims.getSubject()));

        } catch (Exception e) {

          return Optional.empty();
        }
    }

}
