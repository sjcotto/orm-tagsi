package com.example.tarea.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the estudiante database table.
 * 
 */
@Entity
public class Estudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngreso;

	private String matricula;

	//bi-directional one-to-one association to Persona
	@OneToOne(cascade = {CascadeType.ALL })
	@JoinColumn(name="id")
	private Persona persona;

	//bi-directional many-to-one association to Inscripcione
	@OneToMany(mappedBy="estudianteBean")
	private List<Inscripcione> inscripciones;

	public Estudiante() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Inscripcione> getInscripciones() {
		return this.inscripciones;
	}

	public void setInscripciones(List<Inscripcione> inscripciones) {
		this.inscripciones = inscripciones;
	}

}