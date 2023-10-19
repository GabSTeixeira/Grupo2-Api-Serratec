package br.com.loja_gp2.loja_gp2.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Metodo principal onde toda requisição bate antes de chegar no controller.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
            // 1° Pegar o token da requição...
            String token = obterToken(request);

            // 2° Pegar o id do token.
            Optional<Long> id = jwtService.obterIdDoUsuario(token);

            // 3° Saber se o id existe, se veio algum id no token.
            if(id.isPresent()){

                // Pego o usuario dono do token pelo seu id.
                UserDetails usuario = customUserDetailsService.obterUsuarioPeloId(id.get());

                // Nesse ponto verificamos se o usuario está autenticado ou não.
                // Aqui também poderiamos validar as permissões.
                UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // Mudo a autenticação para a propria requisicao.
                autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Repasso a autenticação para o contexto do srping security
                // A partir de agora o spring toma conta de tudo pra mim.
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }

            // 4° filtrar regras do usuario... 
            filterChain.doFilter(request, response);

    }

    private String obterToken(HttpServletRequest request){

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        // Bearer asuhdhausduhsad1asd4asd4as5d2sad4sa4.dasa5sd1asd4.adsd1as5d1as1d1asd
        return token.substring(7);
    }
    
}
