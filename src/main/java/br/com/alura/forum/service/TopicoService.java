package br.com.alura.forum.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository) {this.topicoRepository = topicoRepository;}


    @Transactional
    public TopicoDto atualiza(Long id, AtualizaTopicoForm form) {
        final Topico topico = getTopico(id);

        topico.setTitulo(form.getTitulo());
        topico.setMensagem(form.getMensagem());

        return new TopicoDto(topico);

    }

    private Topico getTopico(Long id) {
        return topicoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
