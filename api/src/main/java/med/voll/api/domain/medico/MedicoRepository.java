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
    // o subselect desconsidere consultas canceladas
        @Query("""
                select m from Medico m
                where
                m.ativo = 1
                and
                m.especialidade = :especialidade
                and
                m.id not in(
                    select c.medico.id from Consulta c
                    where
                    c.data = :data
            and
                    c.motivoCancelamento is null
                )
                order by rand()
                limit 1
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
