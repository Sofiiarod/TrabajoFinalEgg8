package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scire.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

}
