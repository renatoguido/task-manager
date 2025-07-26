package com.rtz.task_manager.controller;

import com.rtz.task_manager.model.TarefaDTO;
import com.rtz.task_manager.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {
    @InjectMocks
    private TarefaController controller;

    @Mock
    private TarefaService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar() {
        TarefaDTO t = new TarefaDTO(null, "T1", "Desc", LocalDate.now(), "TI", 2, false, null);
        when(service.salvar(t)).thenReturn(new TarefaDTO(1L, "T1", "Desc", LocalDate.now(), "TI", 2, false, null));

        var resp = controller.criar(t);

        assertEquals("T1", resp.getTitulo());
        verify(service).salvar(t);
    }

    @Test
    void pendentes() {
        TarefaDTO t = new TarefaDTO(1L, "T1", "Desc", LocalDate.now(), "TI", 2, false, null);
        when(service.pendentes()).thenReturn(List.of(t));

        var resp = controller.pendentes();

        assertEquals(1, resp.size());
        verify(service).pendentes();
    }
}