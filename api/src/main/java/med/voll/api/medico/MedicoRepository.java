package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

// vamos herdar essa interface
// tipo da entidade e tipo do atributo da chave primaria
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
