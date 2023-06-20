package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository constultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){
        // verficar se ja existe 
        if(pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        // isso pois o medico pode ser nulo 
        if(dados.idMedico() != null && medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }
        // executa as regras de negocio e validacoes da aplicacao 
        // salvar agendamento no banco de dados
        // precisamos setar no objeto as entidades e relacionamentos 
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data()); 
        constultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        // verificar se esta chegando o id do medico 
        // escolher um aleatorio 
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        // caso chegue nulo 
        // temos que informar a especialidade do medico 
        // se nao tiver id do medico 
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido.");
        }

        // precisamos escolher um medico aleatorio de acordo com a especialidade
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());


    }
}
