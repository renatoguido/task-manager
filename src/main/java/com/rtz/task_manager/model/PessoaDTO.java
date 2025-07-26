package com.rtz.task_manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String departamento;
    @OneToMany(mappedBy = "pessoa")
    @JsonManagedReference
    private List<TarefaDTO> tarefas;
}
