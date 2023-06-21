package med.voll.api.domain.consulta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.medico.Especialidade;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

// subir o contexto completo do spring 
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    // vamos chamar o controller, precisamos simular uma requisição na nossa api 
    // podemos subir um servidor web e disparar uma requisição de verdade (Teste de Integração)
    // ou fazer um  teste de unidade usando mock , para simular o comportamento da controller (Teste de Unidade) 
    @Test
    @DisplayName("Deveria devolver código 400 quando informações estão inválidas")    
    // precisamos que o usuario esteja logado com esse @
    @WithMockUser
    void agendarCenario1() throws Exception {
        // performar requisição na nossa api 
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    // vamos fazer um teste utilizando o json 
    @Test
    @DisplayName("Deveria devolver código 200 quando informações estão válidas")    
    // precisamos que o usuario esteja logado com esse @
    @WithMockUser
    void agendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        // usar mockito
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

      var response = mvc
            .perform(
                post("/consultas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dadosAgendamentoConsultaJson.write(
                        new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
                    ).getJson())
            )
            .andReturn().getResponse();

        assertThat(((MockHttpServletResponse) response).getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                new DadosDetalhamentoConsulta(null, 2l, 5l, data))
                .getJson();

        assertThat(((MockHttpServletResponse) response).getContentAsString()).isEqualTo(jsonEsperado);
    }
}
