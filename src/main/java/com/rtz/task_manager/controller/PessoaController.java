package com.rtz.task_manager.controller;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public PessoaDTO criar(@RequestBody PessoaDTO pessoa) {
        return pessoaService.salvar(pessoa);
    }

    @PutMapping("/{id}")
    public PessoaDTO atualizar(@PathVariable Long id, @RequestBody PessoaDTO pessoa) {
        return pessoaService.atualizar(id, pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return pessoaService.listarComHoras();
    }

    @GetMapping("/gastos")
    public Double media(@RequestParam String nome, @RequestParam String inicio, @RequestParam String fim) {
        return pessoaService.mediaHoras(nome, inicio, fim);
    }

    @GetMapping("/departamentos")
    public List<Map<String, Object>> departamentos() {
        return pessoaService.listarDepartamentos();
    }
}
