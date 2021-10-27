package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scire.entidades.Creador;

public interface CreadorRepositorio extends JpaRepository<Creador,String> {

}
