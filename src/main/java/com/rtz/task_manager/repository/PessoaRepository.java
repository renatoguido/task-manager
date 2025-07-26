package com.rtz.task_manager.repository;

import com.rtz.task_manager.model.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<PessoaDTO, Long> {
    List<PessoaDTO> findByDepartamento(String departamento);
}
