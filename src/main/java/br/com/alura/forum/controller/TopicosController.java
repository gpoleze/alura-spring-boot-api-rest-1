package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping(value = "/topicos", produces = "application/json")
public class TopicosController {

    private final TopicoRepository topicoRepo;
    private final CursoRepository cursoRepo;

    public TopicosController(TopicoRepository topicoRepo, CursoRepository cursoRepo) {
        this.topicoRepo = topicoRepo;
        this.cursoRepo = cursoRepo;
    }

    @GetMapping
    public List<TopicoDto> listaTodos(@PageableDefault Pageable pageable) {
        final Page<Topico> topicoPage = topicoRepo.findAll(pageable);
        return TopicoDto.converteLista(topicoPage.getContent());
    }

    @GetMapping(params = "nomeCurso")
    public List<TopicoDto> lista(@RequestParam(value = "nomeCurso") String nomeCurso) {
        System.out.println(nomeCurso);
        List<Topico> topicos = topicoRepo.findByCurso_Nome(nomeCurso);

        return TopicoDto.converteLista(topicos);
    }

    @GetMapping("/{id}")
    public TopicoDto carregaTopico(@PathVariable Long id) {
        System.out.println(id);
        Optional<Topico> topico = topicoRepo.findById(id);
        if (topico.isPresent())
            return new TopicoDto(topico.get());

        return null;
    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) throws MethodArgumentNotValidException {
        final Topico topico = form.converte(cursoRepo);
        final Topico topicoSalvo = topicoRepo.save(topico);
        final URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }
}
