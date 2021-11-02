package com.scire.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Curso {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")

	private String id;

	private String nombre;
	private String descripcion;
	private String url;
	private Boolean estado;

	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Categoria categoria;
	@ManyToOne
	private Creador creador;

	public Curso() {
		super();
	}

	public Curso(String id, String nombre, String descripcion, String url, Boolean estado, Categoria categoria,
			Creador creador) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
		this.estado = estado;
		
		this.categoria = categoria;
		this.creador = creador;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Creador getCreador() {
		return creador;
	}

	public void setCreador(Creador creador) {
		this.creador = creador;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", url=" + url + ", estado="
				+ estado + ", usuario=" + usuario + ", categoria=" + categoria + ", creador=" + creador + "]";
	}

}
