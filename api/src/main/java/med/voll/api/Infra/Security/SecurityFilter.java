package med.voll.api.Infra.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.Usuario.UsuarioRepository;

@Component
// OncePerRequestFilter: garante que ela apenas vai ser executada uma unica vez para cada requisicao
public class SecurityFilter extends OncePerRequestFilter{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;
    // request: pega coisas da requisição 
    // response: enviar coisas na resposta 
    // filterChain: cadeia de filtros da aplicação 
    // temos que encaminhar para os proximos filtros da aplicação 
    // assim o corpo vai parar de vir vazio 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            //  vamos capturar o token e validar se esta certo ou nao 
            // o envio do token é feito atraves de um cabeçalho http
            // cabeçalho autorization 
            var tokenJWT = recuperarToken(request);
            // recuperar um subject e validar o token
            if(tokenJWT != null){
                var subject = tokenService.getSubject(tokenJWT);
                // temos que falar para ele que o token esta certo, portanto a pessoa esta logada 
                // autenticação forçada da pessoa, o token esta validado e eu chequei que a pessoa esta logada 
                var usuario = repository.findByLogin(subject);
                // recebe o objeto do usuario, credenciais e um conjunto de autorizações 
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // se chegar ate aqui, siginifica que o token esta valido, portanto: 
            // temos que avisar que o usuario esta logado 
            filterChain.doFilter(request, response);
    }
    // ela é uma classe para que o spring carregue um componente generico
    // usar @component

    private String recuperarToken(HttpServletRequest request) {
        // getHeader para pegar cabeçalho 
        var autorizationHeader = request.getHeader("Authorization");
        if(autorizationHeader != null){
            return autorizationHeader.replace("Bearer", "");
        }
        return null;
    }
}
