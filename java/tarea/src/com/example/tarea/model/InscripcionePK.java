package com.example.tarea.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the inscripciones database table.
 * 
 */
@Embeddable
public class InscripcionePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int codigo;

	private int estudiante;

	public InscripcionePK() {
	}
	public int getCodigo() {
		return this.codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getEstudiante() {
		return this.estudiante;
	}
	public void setEstudiante(int estudiante) {
		this.estudiante = estudiante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InscripcionePK)) {
			return false;
		}
		InscripcionePK castOther = (InscripcionePK)other;
		return 
			(this.codigo == castOther.codigo)
			&& (this.estudiante == castOther.estudiante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigo;
		hash = hash * prime + this.estudiante;
		
		return hash;
	}
}