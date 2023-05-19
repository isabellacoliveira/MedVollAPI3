package med.voll.api.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    // consulta do usuario no banco de dados 
    UserDetails findByLogin(String login);
    
}
