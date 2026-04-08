package com.example.crud_leticia.service;

import com.example.crud_leticia.dto.PessoaRequest;
import com.example.crud_leticia.model.Pessoa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    private final File arquivo = new File("pessoas.json");
    private final ObjectMapper mapper = new ObjectMapper();


    private List<Pessoa> lerArquivo() {
        try {
            if (!arquivo.exists()) {
                return new ArrayList<>();
            }
            return mapper.readValue(arquivo, new TypeReference<List<Pessoa>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private void salvarArquivo(List<Pessoa> lista) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Pessoa> listar() {
        return lerArquivo();
    }


    public Pessoa criar(PessoaRequest request) {
        List<Pessoa> lista = lerArquivo();

        Pessoa pessoa = new Pessoa();

        pessoa.setId(UUID.randomUUID().toString());
        pessoa.setNome(request.getNome());
        pessoa.setTelefone(request.getTelefone());

        lista.add(pessoa);
        salvarArquivo(lista);

        return pessoa;


    }


    public Pessoa atualizar(String id, PessoaRequest request) {
        List<Pessoa> lista = lerArquivo();

        for (Pessoa p : lista) {
            if (p.getId().equals(id)) {
                p.setNome(request.getNome());
                p.setTelefone(request.getTelefone());
                salvarArquivo(lista);
                return p;
            }
        }

        throw new RuntimeException("Pessoa não encontrada");
    }


    public String deletar(String id) {
        List<Pessoa> lista = lerArquivo();

        boolean removido = lista.removeIf(p -> p.getId().equals(id));
        salvarArquivo(lista);

        if (removido) {
            return "Pessoa deletada com sucesso";
        } else {
            return "Pessoa não encontrada";
        }
    }
}