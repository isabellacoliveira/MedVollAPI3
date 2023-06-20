package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.ValidacaoException;

public class ValidadorHorarioFuncionamentoClinica {
    public void validar(DadosAgendamentoConsulta dados){
        // validar a data que esta chegando no dto 
        var dataConsulta = dados.data();    

        // checar se o dia é domingo
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        // vamos colocar o horario de funcionamento da clinica 
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if(domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
