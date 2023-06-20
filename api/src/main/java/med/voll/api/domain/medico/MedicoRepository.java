package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// vamos herdar essa interface
// tipo da entidade e tipo do atributo da chave primaria
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    // jpql: java persistence query language
    // text block 
    // carregar os medicos que estejam ativos e tenham essa especialidade 
    // ele vai trazer só um devido ao limit e ordenar rand (aleatorio)
    // deve ser um medico que esteja livre nessa data, ou seja 
    // vamos trazer o medico cujo o id nao esteja contido no sub select 
        @Query("""
            SELECT m FROM Medico m
            WHERE
            m.ativo = 1
            AND 
            m.id NOT IN (
                SELECT c.medico.id FROM Consulta c
                WHERE 
                c.data = :data
            )
            AND m.especialidade = :especialidade
            ORDER BY rand()
            LIMIT 1 
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo
            from Medico m
            where 
            m.id = :id
            """)
            Boolean findAtivoById(Long id);
}
