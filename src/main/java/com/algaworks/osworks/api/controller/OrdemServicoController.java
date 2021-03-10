package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.algaworks.osworks.api.model.OrdemServicoInput;
import com.algaworks.osworks.api.model.OrdemServicoModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServico;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput){
        return toModel(gestaoOrdemServico.criar(toEntity(ordemServicoInput)));
    }

    @GetMapping
    public List<OrdemServicoModel> listar(){
        return toCollectionModel(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

        return ordemServico.map(servico -> ResponseEntity.ok(toModel(servico))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
        return ordensServico.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

//        return ordensServico.stream()
//                .map(ordemServico -> toModel(ordemServico))
//                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
        return modelMapper.map(ordemServicoInput, OrdemServico.class);
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId) {
        gestaoOrdemServico.finalizar(ordemServicoId);
    }
}
