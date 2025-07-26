package com.rtz.task_manager.service;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaDTO salvar(PessoaDTO pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public PessoaDTO atualizar(Long id, PessoaDTO dados) {
        PessoaDTO pessoa = pessoaRepository.findById(id).orElseThrow();
        pessoa.setNome(dados.getNome());
        pessoa.setDepartamento(dados.getDepartamento());
        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<Map<String, Object>> listarComHoras() {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (PessoaDTO p : pessoaRepository.findAll()) {
            int totalHoras = 0;
            for (TarefaDTO t : p.getTarefas()) {
                if (t.getDuracao() != null) {
                    totalHoras += t.getDuracao();
                }
            }
            Map<String, Object> item = new HashMap<>();
            item.put("nome", p.getNome());
            item.put("departamento", p.getDepartamento());
            item.put("totalHoras", totalHoras);
            resultado.add(item);
        }
        return resultado;
    }

    public Double mediaHoras(String nome, String inicio, String fim) {
        LocalDate d1 = LocalDate.parse(inicio);
        LocalDate d2 = LocalDate.parse(fim);

        for (PessoaDTO p : pessoaRepository.findAll()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                int soma = 0, cont = 0;
                for (TarefaDTO t : p.getTarefas()) {
                    if (t.getPrazo() != null &&
                            !t.getPrazo().isBefore(d1) &&
                            !t.getPrazo().isAfter(d2) &&
                            t.getDuracao() != null) {
                        soma += t.getDuracao();
                        cont++;
                    }
                }
                return cont > 0 ? (double) soma / cont : 0.0;
            }
        }
        return 0.0;
    }

    public List<Map<String, Object>> listarDepartamentos() {
        Map<String, int[]> mapa = new HashMap<>();
        for (PessoaDTO p : pessoaRepository.findAll()) {
            mapa.putIfAbsent(p.getDepartamento(), new int[2]);
            mapa.get(p.getDepartamento())[0]++;
            mapa.get(p.getDepartamento())[1] += p.getTarefas().size();
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (String dep : mapa.keySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("departamento", dep);
            item.put("pessoas", mapa.get(dep)[0]);
            item.put("tarefas", mapa.get(dep)[1]);
            resultado.add(item);
        }
        return resultado;
    }
}

