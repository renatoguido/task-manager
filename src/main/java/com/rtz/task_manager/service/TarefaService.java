package com.rtz.task_manager.service;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.repository.PessoaRepository;
import com.rtz.task_manager.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired private PessoaRepository pessoaRepository;

    public TarefaDTO salvar(TarefaDTO tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public TarefaDTO alocar(Long id) {
        TarefaDTO tarefa = tarefaRepository.findById(id).orElseThrow();
        List<PessoaDTO> pessoas = pessoaRepository.findAll();

        for (PessoaDTO pessoa : pessoas) {
            if (pessoa.getDepartamento().equalsIgnoreCase(tarefa.getDepartamento())) {
                tarefa.setPessoa(pessoa);  // Aqui você deve passar a pessoa, não a tarefa!
                return tarefaRepository.save(tarefa);
            }
        }

        throw new RuntimeException("Nenhuma pessoa disponível no departamento");
    }

    public TarefaDTO finalizar(Long id) {
        TarefaDTO tarefa = tarefaRepository.findById(id).orElseThrow();
        tarefa.setFinalizado(true);
        return tarefaRepository.save(tarefa);
    }

    public List<TarefaDTO> pendentes() {
        return tarefaRepository.findAll().stream()
                .filter(t -> t.getPessoa() == null && !t.getFinalizado())
                .sorted(Comparator.comparing(TarefaDTO::getPrazo))
                .limit(3)
                .toList();
    }
}
