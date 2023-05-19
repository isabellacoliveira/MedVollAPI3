package med.voll.api.domain.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// todas as classes do spring tem que ter uma notação em cima 
@Service
public class AutenticacaoService implements UserDetailsService{
    // injetar dependencia do repository
    @Autowired
    private UsuarioRepository repository;


    @Override
    // método chamado toda vez que fazemos login  
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
        // throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    // precisa saber que essa classe é a que faz uma autenticação 
}
