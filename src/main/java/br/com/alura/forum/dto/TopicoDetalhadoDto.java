package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.StatusTopico;
import br.com.alura.forum.model.Topico;

public class TopicoDetalhadoDto {
    private final Long id;
    private final String titulo;
    private final String mensagem;
    private final LocalDateTime dataCriacao;
    private final String nomeAutor;
    private final StatusTopico status;
    private final List<RespostaDto> respostas;

    public TopicoDetalhadoDto(Topico topico) {
        id = topico.getId();
        titulo = topico.getTitulo();
        mensagem = topico.getMensagem();
        dataCriacao = topico.getDataCriacao();
        nomeAutor = topico.getAutor().getNome();
        status = topico.getStatus();
        respostas = topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList());
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

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }
}
