package com.example.tarea.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the curso database table.
 * 
 */
@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int codigo;

	private String creditos;

	private String nombre;

	//bi-directional many-to-one association to Profesor
	@ManyToOne
	@JoinColumn(name="responsable")
	private Profesor profesor;

	//bi-directional many-to-many association to Profesor
	@ManyToMany(mappedBy="cursos2")
	private List<Profesor> profesors;

	//bi-directional many-to-one association to Inscripcione
	@OneToMany(mappedBy="curso")
	private List<Inscripcione> inscripciones;

	public Curso() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCreditos() {
		return this.creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public List<Profesor> getProfesors() {
		return this.profesors;
	}

	public void setProfesors(List<Profesor> profesors) {
		this.profesors = profesors;
	}

	public List<Inscripcione> getInscripciones() {
		return this.inscripciones;
	}

	public void setInscripciones(List<Inscripcione> inscripciones) {
		this.inscripciones = inscripciones;
	}

}