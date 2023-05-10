package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/medicos")
public class MedicoController<Paginable> {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    // notação pois é um método de adição
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados)
    {
        repository.save(new Medico(dados));
    }
    @GetMapping
    // vamos usar metodos do proprio spring boot para fazer a paginacao 
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados)
    {
        // trazer os dados atuais e sobre escrever o que ja tem la 
        // pegar referencia pelo id 
        // se carregamos uma entidade o update eh feito sozinho 
        var medico  = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
}
