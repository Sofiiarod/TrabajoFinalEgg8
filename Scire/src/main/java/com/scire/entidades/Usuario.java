package com.scire.entidades;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.scire.roles.Rol;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")

	private String id;
	
	private String nombre;

	private String apellido;

	private String email;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;

	private String clave;
	
	@Temporal(TemporalType.DATE)
	private Date fechaCreado;
	
	private Boolean alta;

	public Usuario() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return  Objects.equals(email, other.email);
		
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", rol="
				+ rol + ", clave=" + clave + ", fechaCreado=" + fechaCreado + ", alta=" + alta + "]";
	}

	public Usuario(String nombre, String apellido, String email, Rol rol, String clave, Date fechaCreado,
			Boolean alta) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.rol = rol;
		this.clave = clave;
		this.fechaCreado = fechaCreado;
		this.alta = alta;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
}
