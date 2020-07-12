package br.com.alura.forum.config.validacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.exception.ElementNotInTheDatabaseException;

@RestControllerAdvice
public class ErroValidacaoHandler {

    private final MessageSource messageSource;

    @Autowired
    public ErroValidacaoHandler(MessageSource messageSource) {this.messageSource = messageSource;}

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return fieldErrors
                .stream()
                .map(fieldError -> {
                    String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return new ErroDeFormularioDto(fieldError.getField(), mensagem);
                })
                .collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ElementNotInTheDatabaseException.class})
    public ErroDeFormularioDto handle(ElementNotInTheDatabaseException exception) {
        return new ErroDeFormularioDto(exception.getFieldError().getField(),exception.getMessage());
    }

}
