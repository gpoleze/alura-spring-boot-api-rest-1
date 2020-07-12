package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController("/topicos")
public class TopicosController {

    private final TopicoRepository topicoRepo;

    public TopicosController(TopicoRepository topicoRepo) {this.topicoRepo = topicoRepo;}

    @GetMapping
    public List<TopicoDto> listaTodos(@PageableDefault Pageable pageable) {
        System.out.println(pageable);
        final Page<Topico> topicoPage = topicoRepo.findAll(pageable);
        return TopicoDto.converteLista(topicoPage.getContent());
    }

    @GetMapping(params = "nomeCurso")
    public List<TopicoDto> lista(@RequestParam(value = "nomeCurso") String nomeCurso) {
        System.out.println(nomeCurso);
        List<Topico> topicos = topicoRepo.findByCurso_Nome(nomeCurso);

        return TopicoDto.converteLista(topicos);
    }
}
