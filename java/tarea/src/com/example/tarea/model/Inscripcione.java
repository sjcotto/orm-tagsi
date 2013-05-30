package com.example.tarea.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the inscripciones database table.
 * 
 */
@Entity
@Table(name="inscripciones")
public class Inscripcione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InscripcionePK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	//bi-directional many-to-one association to Estudiante
	@ManyToOne 
	@JoinColumn(name="estudiante",insertable = false, updatable = false)
	private Estudiante estudianteBean;

	//bi-directional many-to-one association to Curso
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="codigo",insertable = false, updatable = false)
	private Curso curso;

	public Inscripcione() {
	}

	public InscripcionePK getId() {
		return this.id;
	}

	public void setId(InscripcionePK id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Estudiante getEstudianteBean() {
		return this.estudianteBean;
	}

	public void setEstudianteBean(Estudiante estudianteBean) {
		this.estudianteBean = estudianteBean;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}