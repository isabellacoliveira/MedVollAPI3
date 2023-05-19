package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.Infra.Security.TokenService;
import med.voll.api.domain.Usuario.DadosAutenticacao;
import med.voll.api.domain.Usuario.Usuario;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    // precisamos chamar uma classe do proprio spring que é quem chama o processo de login 
    @Autowired
    private AuthenticationManager maneger;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
       var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
       var authentication = maneger.authenticate(token);

        // nossa controller precisa retornar o token
        // precisamos gerar esse token, usando a bibioteca json web token
        // getPrincipal = pega usuario logado 
       return ResponseEntity.ok(tokenService.gerarToken((Usuario) authentication.getPrincipal()));
    }
}
