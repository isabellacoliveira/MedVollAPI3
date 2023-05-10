package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
    @NotNull
    // id eh obrigatorio    
    Long id, 
    String nome, 
    String telefone, 
    DadosEndereco endereco) {
    
}
