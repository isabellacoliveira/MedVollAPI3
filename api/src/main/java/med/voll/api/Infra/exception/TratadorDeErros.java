package med.voll.api.Infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// temos que colocar isso para que essa classe seja carregada 
@RestControllerAdvice
public class TratadorDeErros {
    // dizer quando o metodo tem que ser chamado, qual excessao ele vai tratar 
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        // build para criar o erro
        return ResponseEntity.notFound().build();
    }
    // erro de validacao de dados
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        // dentro do body, capturar o erro lançado e formar o corpo que ela deve devolver
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    // as informacoes que serao retornadas em caso de erro 
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
