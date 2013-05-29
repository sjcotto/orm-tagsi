package com.example.tarea.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.tarea.interfaces.Api;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;
import com.example.tarea.model.Persona;

public class JpaController implements Api {

	public JpaController() {
		try {
			setUp();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private EntityManagerFactory entityManagerFactory;

	protected void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("EM");
	}

	private Object save(Object o) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();
		entityManager.close();
		return o;
	}

	@SuppressWarnings("unused")
	private void update(Object o) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		o = entityManager.merge(o);
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void registrarEstudiante(Estudiante est) {
		Persona p = (Persona) save(est.getPersona());
		System.out.println(p.getId());
		est.setPersona(p);
		est.setId(p.getId());
		save(est);
	}

	@Override
	public void inscribirEstudianteCurso(int estId, Inscripcione c) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		
		Estudiante est = entityManager.find(Estudiante.class, estId);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		est.getInscripciones().add(c);
		this.update(est);
	}

	@Override
	public List<Estudiante> buscarPorApellido(String apellido) {

		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("Select u from Estudiante u where u.persona.apellido = '"+apellido+"'");
		System.out.println(query.toString());
		List list = query.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return list;
		
	}

	@Override
	public List<Inscripcione> obtenerCursosPorEstudiante(int estId) {

		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		
		Estudiante est = entityManager.find(Estudiante.class, estId);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		List<Inscripcione> list = est.getInscripciones();
		
		return list;
		
		
	}

	@Override
	public void registrarCurso(Curso c) {
		save(c);
	}

	@Override
	public List<Curso> obtenerCursosMenosInscriptos() {

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Curso> obtenerCursos() {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("Select u from Curso u ");
		List list = query.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return list;
	}

}
