package com.scire.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scire.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

	@Query("SELECT a from Usuario a WHERE a.email LIKE :email")
	public Usuario encontrarPorEmail(@Param("email") String email);
	
	
}
