package br.com.alura.forum.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.exception.ElementNotInTheDatabaseException;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }


    @Transactional
    public Optional<TopicoDto> atualiza(Long id, AtualizaTopicoForm form) {
        final Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (!topicoOptional.isPresent())
            return Optional.empty();


        Topico topico = topicoOptional.get();

        topico.setTitulo(form.getTitulo());
        topico.setMensagem(form.getMensagem());

        return Optional.of(new TopicoDto(topico));
    }

    @Transactional
    public Optional<Boolean> remove(Long id) {
        if (!topicoRepository.findById(id).isPresent())
            return Optional.empty();

        topicoRepository.deleteById(id);
        return Optional.of(true);
    }

    @Transactional
    public TopicoDto salva(TopicoForm form) {

        final Curso curso = cursoRepository
                .findByNome(form.getNomeCurso())
                .orElseThrow(() -> new ElementNotInTheDatabaseException("curso", "nomeCurso"));

        final Topico topico = form.converte(curso);
        final Topico topicoSalvo = topicoRepository.save(topico);

        return new TopicoDto(topicoSalvo);
    }
}
