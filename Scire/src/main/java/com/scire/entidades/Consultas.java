package com.scire.entidades;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

public class Consultas {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	
	private String consulta;

	public Consultas(String consulta) {
		super();
		this.consulta = consulta;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	@Override
	public String toString() {
		return "Consultas [consulta=" + consulta + ", getConsulta()=" + getConsulta() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}

