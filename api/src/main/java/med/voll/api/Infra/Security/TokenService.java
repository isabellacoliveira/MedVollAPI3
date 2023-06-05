package med.voll.api.Infra.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.domain.Usuario.Usuario;

@Service
public class TokenService {
    // // tudo o que tem haver com token
    // public String gerarToken(Usuario usuario) { 
    //     try {
    //         // esse método retorna uma string que é a chave secreta para fazer a geração do token
    //         var algoritmo = Algorithm.HMAC256("123456");
    //         return JWT.create()
    //             .withIssuer("API Voll.med")
    //             // vamos adicionar informações dentro do token
    //             .withSubject(usuario.getLogin())
    //             .withClaim("id", usuario.getId())
    //             // o ideal é que tenhamos uma validade (2h)
    //             .withExpiresAt(dataExpiracao())
    //             .sign(algoritmo);
    //     } catch (JWTCreationException exception){
    //         throw new RuntimeException("erro ao gerrar token jwt", exception);
    //     }        
    // }
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.require(algoritmo)
                                .withIssuer("API Voll.med")
                                .build()
                                // verificar se o token esta valido
                                .verify(tokenJWT)
                                .getSubject();
        } catch (JWTVerificationException exception) {
                throw new RuntimeException("Token JWT inválido ou expirado!");
        }
}

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
