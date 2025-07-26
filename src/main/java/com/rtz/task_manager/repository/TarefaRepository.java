package com.rtz.task_manager.repository;

import com.rtz.task_manager.model.TarefaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<TarefaDTO, Long> {

}

