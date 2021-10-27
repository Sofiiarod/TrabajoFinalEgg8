package com.scire.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scire.entidades.Categoria;
import com.scire.entidades.Creador;
import com.scire.entidades.Curso;
import com.scire.entidades.Usuario;
import com.scire.errores.ErrorException;
import com.scire.repositorios.CursoRepositorio;

@Service
public class CursoServicio {

	@Autowired
	CursoRepositorio cursorepo;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class,Exception.class })
	public void guardar(String nombre,String descripcion,String url,Categoria categoriaID,Creador creadorID) throws ErrorException {
		
			Curso curso = new Curso();
			curso.setNombre(nombre);
			curso.setDescripcion(descripcion);
			curso.setUrl(url);
			curso.setEstado(true);
			curso.setCategoria(categoriaID);
			curso.setCreador(creadorID);
			cursorepo.save(curso);
	}
	
}
