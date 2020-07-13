package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AtualizaTopicoForm {

    @NotEmpty
    @Size(min = 5)
    private final String titulo;

    @NotEmpty
    @Size(min = 5)
    private final String mensagem;

    public AtualizaTopicoForm(@NotEmpty @Size(min = 5) String titulo, @NotEmpty @Size(min = 5) String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
