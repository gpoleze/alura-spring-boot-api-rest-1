package br.com.alura.forum.controller.form;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.exception.ElementNotInTheDatabaseException;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;

public class TopicoForm {

    @NotEmpty
    @Size(min = 5)
    private final String titulo;

    @NotEmpty
    @Size(min = 5)
    private final String mensagem;

    @NotEmpty
    private final String nomeCurso;

    public TopicoForm(String titulo, String mensagem, String nomeCurso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.nomeCurso = nomeCurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicoForm that = (TopicoForm) o;
        return Objects.equals(titulo, that.titulo) &&
                Objects.equals(mensagem, that.mensagem) &&
                Objects.equals(nomeCurso, that.nomeCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, mensagem, nomeCurso);
    }

    public Topico converte(CursoRepository repository) {
        final Curso curso = repository
                .findByNome(nomeCurso)
                .orElseThrow(() -> new ElementNotInTheDatabaseException("curso", "nomeCurso"));

        return new Topico(titulo, mensagem, curso);
    }
}
