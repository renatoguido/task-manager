package com.rtz.task_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarefaDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private String departamento;
    private Integer duracao; // em horas
    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference
    private PessoaDTO pessoa;
}
