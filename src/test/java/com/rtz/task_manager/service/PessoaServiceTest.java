package com.rtz.task_manager.service;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.repository.PessoaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void testSalvarPessoa() {
        PessoaDTO pessoa = new PessoaDTO(null, "João", "TI", new ArrayList<>());
        PessoaDTO pessoaSalva = new PessoaDTO(1L, "João", "TI", new ArrayList<>());

        when(pessoaRepository.save(pessoa)).thenReturn(pessoaSalva);

        PessoaDTO resultado = pessoaService.salvar(pessoa);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João", resultado.getNome());
    }

    @Test
    void testAtualizarPessoa() {
        PessoaDTO pessoaExistente = new PessoaDTO(1L, "Maria", "RH", new ArrayList<>());
        PessoaDTO dadosAtualizados = new PessoaDTO(null, "Maria Silva", "RH", new ArrayList<>());

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(any(PessoaDTO.class))).thenAnswer(i -> i.getArgument(0));

        PessoaDTO resultado = pessoaService.atualizar(1L, dadosAtualizados);

        assertEquals("Maria Silva", resultado.getNome());
    }

    @Test
    void testDeletarPessoa() {
        doNothing().when(pessoaRepository).deleteById(1L);

        pessoaService.deletar(1L);

        verify(pessoaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListarComHoras() {
        PessoaDTO pessoa = new PessoaDTO(1L, "João", "TI", List.of(
                new TarefaDTO(1L, "Tarefa 1", "Desc", LocalDate.now(), "TI", 3, false, null)
        ));

        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        var lista = pessoaService.listarComHoras();

        assertFalse(lista.isEmpty());
        assertEquals("João", lista.get(0).get("nome"));
        assertEquals(3, lista.get(0).get("totalHoras"));
    }
}