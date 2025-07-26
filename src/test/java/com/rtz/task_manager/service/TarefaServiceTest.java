package com.rtz.task_manager.service;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.repository.PessoaRepository;
import com.rtz.task_manager.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {
    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void testSalvarTarefa() {
        TarefaDTO t = new TarefaDTO(null, "Titulo", "Descricao", LocalDate.now(), "TI", 2, false, null);
        when(tarefaRepository.save(any())).thenReturn(new TarefaDTO(1L, "Titulo", "Descricao", LocalDate.now(), "TI", 2, false, null));
        TarefaDTO salva = tarefaService.salvar(t);
        assertNotNull(salva);
    }

    @Test
    void testAlocar() {
        PessoaDTO pessoa = new PessoaDTO(1L, "Renato", "TI", new ArrayList<>());
        TarefaDTO tarefa = new TarefaDTO(1L, "Titulo", "Desc", LocalDate.now(), "TI", 2, false, null);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        when(tarefaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaDTO tarefaAlocada = tarefaService.alocar(1L);

        assertNotNull(tarefaAlocada.getPessoa());
        assertEquals("Renato", tarefaAlocada.getPessoa().getNome());
    }

    @Test
    void testFinalizarTarefa() {
        TarefaDTO t = new TarefaDTO(1L, "Titulo", "Desc", LocalDate.now(), "TI", 2, false, null);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(t));
        when(tarefaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaDTO finalizada = tarefaService.finalizar(1L);
        assertTrue(finalizada.getFinalizado());
    }
}