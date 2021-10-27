package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scire.entidades.Curso;

public interface CursoRepositorio extends JpaRepository<Curso,String> {

}
