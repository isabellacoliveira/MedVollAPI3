package med.voll.api.controller;

import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    // serve para fazer a chamada injeção de dependências
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dados)
    {
        // precisamos dizer a ele para instanciar o objeto
        // criar o objeto e passar para o controller
        // passar null pois vai representar o id gerado pelo bd
        // criamos um construtor dentro da entidade medico
        repository.save(new Medico(dados));
    }
}
