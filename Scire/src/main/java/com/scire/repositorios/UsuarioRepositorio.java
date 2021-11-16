package com.scire.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scire.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

	@Query("SELECT a from Usuario a WHERE a.email LIKE :email")
	public Usuario buscarPorEmail(@Param("email") String email);
	
	@Query("SELECT a from Usuario a WHERE a.nombre LIKE :nombre")
	public Usuario buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT a from Usuario a WHERE a.apellido LIKE :apellido")
	public Usuario buscarPorApellido(@Param("apellido") String apellido);
	
public List<Usuario> findByEmail(String email);
}
