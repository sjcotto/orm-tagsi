package com.example.tarea.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the profesor database table.
 * 
 */
@Entity
public class Profesor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String grado;

	//bi-directional many-to-one association to Curso
	@OneToMany(mappedBy="profesor")
	private List<Curso> cursos1;

	//bi-directional many-to-many association to Curso
	@ManyToMany
	@JoinTable(
		name="ensenan"
		, joinColumns={
			@JoinColumn(name="ensenan")
			}
		, inverseJoinColumns={
			@JoinColumn(name="codigo")
			}
		)
	private List<Curso> cursos2;

	//bi-directional one-to-one association to Persona
	@OneToOne
	@JoinColumn(name="id")
	private Persona persona;

	public Profesor() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public List<Curso> getCursos1() {
		return this.cursos1;
	}

	public void setCursos1(List<Curso> cursos1) {
		this.cursos1 = cursos1;
	}

	public List<Curso> getCursos2() {
		return this.cursos2;
	}

	public void setCursos2(List<Curso> cursos2) {
		this.cursos2 = cursos2;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}