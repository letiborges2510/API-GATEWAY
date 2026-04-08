package com.example.crud_leticia.controller;

import com.example.crud_leticia.dto.PessoaRequest;
import com.example.crud_leticia.model.Pessoa;
import com.example.crud_leticia.service.PessoaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pessoa> listar() {
        return service.listar();
    }

    @PostMapping(consumes = "application/json")
    public Pessoa criar(@RequestBody PessoaRequest request) {
        return service.criar(request);
    }


    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable String id,
                            @RequestBody PessoaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable String id) {
        return service.deletar(id);
    }
}