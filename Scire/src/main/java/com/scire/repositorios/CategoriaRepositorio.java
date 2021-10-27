package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scire.entidades.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria,String> {

}
