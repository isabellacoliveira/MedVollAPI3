package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// vamos herdar essa interface
// tipo da entidade e tipo do atributo da chave primaria
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

}
