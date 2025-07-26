package com.rtz.task_manager.controller;

import com.rtz.task_manager.model.PessoaDTO;
import com.rtz.task_manager.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @InjectMocks
    private PessoaController controller;

    @Mock
    private PessoaService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar() {
        PessoaDTO p = new PessoaDTO(null, "João", "TI", List.of());
        when(service.salvar(p)).thenReturn(new PessoaDTO(1L, "João", "TI", List.of()));

        PessoaDTO resp = controller.criar(p);

        assertEquals("João", resp.getNome());
        verify(service).salvar(p);
    }

    @Test
    void listar() {
        List<Map<String,Object>> lista = List.of(Map.of("nome", "João"));
        when(service.listarComHoras()).thenReturn(lista);

        var resp = controller.listar();

        assertEquals(1, resp.size());
        verify(service).listarComHoras();
    }

    @Test
    void media() {
        when(service.mediaHoras("João", "2025-01-01", "2025-01-31")).thenReturn(5.0);

        Double resp = controller.media("João", "2025-01-01", "2025-01-31");

        assertEquals(5.0, resp);
        verify(service).mediaHoras("João", "2025-01-01", "2025-01-31");
    }
}