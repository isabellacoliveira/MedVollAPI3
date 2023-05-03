package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @PostMapping
    // request body para dizer que ele deve pegar o dado
    // do corpo da requisição
    public void cadastrar(@RequestBody DadosCadastroMedico json){
        System.out.println(json);
    }
}
