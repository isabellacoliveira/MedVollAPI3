package med.voll.api.domain.consulta.validacoes;

import java.time.LocalDateTime;

import ch.qos.logback.core.util.Duration;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.ValidacaoException;

public class ValidadorHorarioAntecedencia {    
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
        
        if(diferencaEmMinutos < 30){
         throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }
}
}
