package med.voll.api.Infra.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // sinaliza que nós que vamos definir as configurações de segurança 
public class SecurityConfigurations {
    // configurações de segurança 
    // security filter chain : controla autorização e login
    @Bean // devolve um objeto , expor o retorno do metodo 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf : cross-site request forgery - desabilitar 
        // desabilitaremos a proteção pois o próprio token já é uma proteção 
        return http.csrf().disable()
        // estamos desabilitando o login statefull, queremos stateless
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().build();
    }

    @Bean
    // essa classe criará o método autentication manager 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
