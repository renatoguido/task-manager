package com.rtz.task_manager.controller;

import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public TarefaDTO criar(@RequestBody TarefaDTO tarefa) {
        return tarefaService.salvar(tarefa);
    }

    @PutMapping("/alocar/{id}")
    public TarefaDTO alocar(@PathVariable Long id) {
        return tarefaService.alocar(id);
    }

    @PutMapping("/finalizar/{id}")
    public TarefaDTO finalizar(@PathVariable Long id) {
        return tarefaService.finalizar(id);
    }

    @GetMapping("/pendentes")
    public List<TarefaDTO> pendentes() {
        return tarefaService.pendentes();
    }
}
