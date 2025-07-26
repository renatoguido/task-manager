package com.rtz.task_manager.repository;

import com.rtz.task_manager.model.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaDTO, Long> {}
