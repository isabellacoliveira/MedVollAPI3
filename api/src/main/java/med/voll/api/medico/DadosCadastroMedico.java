package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String erm, Especialidade especialidade, DadosEndereco endereco) {
    // campos que recebemos na requisição
}
