package br.com.alura.forum.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.forum.model.Topico;

public class TopicoDto {

    private final Long id;
    private final String titulo;
    private final String mensagem;
    private final LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static List<TopicoDto> converteLista(List<Topico> topicos) {
        return topicos.parallelStream().map(TopicoDto::new).collect(toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
