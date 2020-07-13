package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.dto.TopicoDetalhadoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.service.TopicoService;

@RestController
@RequestMapping(value = "/topicos", produces = "application/json")
public class TopicosController {

    private final TopicoService topicoService;
    private final TopicoRepository topicoRepo;

    @Autowired
    public TopicosController(TopicoService topicoService, TopicoRepository topicoRepo) {
        this.topicoService = topicoService;
        this.topicoRepo = topicoRepo;
    }

    @GetMapping
    public List<TopicoDto> listaTodos(@PageableDefault Pageable pageable) {
        final Page<Topico> topicoPage = topicoRepo.findAll(pageable);
        return TopicoDto.converteLista(topicoPage.getContent());
    }

    @GetMapping(params = "nomeCurso")
    public List<TopicoDto> lista(@RequestParam String nomeCurso) {
        return topicoRepo.findByCurso_Nome(nomeCurso)
                .map(TopicoDto::converteLista)
                .orElseGet(Collections::emptyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalhadoDto> carregaTopico(@PathVariable Long id) {
        return topicoRepo.findById(id)
                .map(topico -> ResponseEntity.ok(new TopicoDetalhadoDto(topico)))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
        TopicoDto topico = topicoService.salva(form);

        final URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> atualiza(@PathVariable Long id, @Valid @RequestBody AtualizaTopicoForm form) {
        return topicoService
                .atualiza(id, form)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return topicoService
                .remove(id)
                .map(teveSucesso -> ResponseEntity.ok().build())
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
