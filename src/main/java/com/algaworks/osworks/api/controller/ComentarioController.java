package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.osworks.api.model.ComentarioInput;
import com.algaworks.osworks.api.model.ComentarioModel;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.model.Comentario;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import org.apache.tomcat.jni.OS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));

        return toCollectionModel(ordemServico.getComentarios());
    }

    private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
        return comentarios.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
             @Valid @RequestBody ComentarioInput comentarioInput) {
        Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
        
        return toModel(comentario);
    }

    private ComentarioModel toModel(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioModel.class);
    }
}
