package com.rtz.task_manager.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new ControllerExceptionHandler();
    }

    @Test
    void testHandleNotFound() {
        NoSuchElementException ex = Mockito.mock(NoSuchElementException.class);
        Mockito.when(ex.getMessage()).thenReturn("Recurso não encontrado");

        ResponseEntity<String> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Recurso não encontrado"));
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = Mockito.mock(IllegalArgumentException.class);
        Mockito.when(ex.getMessage()).thenReturn("Argumento inválido");

        ResponseEntity<String> response = handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Argumento inválido"));
    }

    @Test
    void testHandleRuntime() {
        RuntimeException ex = Mockito.mock(RuntimeException.class);
        Mockito.when(ex.getMessage()).thenReturn("Erro interno");

        ResponseEntity<String> response = handler.handleRuntime(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro interno"));
    }

    @Test
    void testHandleValidation() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getMessage()).thenReturn("Erro de validação detalhado");

        ResponseEntity<String> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro de validação"));
    }

    @Test
    void testHandleGeneric() {
        Exception ex = Mockito.mock(Exception.class);
        Mockito.when(ex.getMessage()).thenReturn("Erro genérico");

        ResponseEntity<String> response = handler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro genérico"));
    }

}