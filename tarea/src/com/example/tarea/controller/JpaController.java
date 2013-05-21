package com.example.tarea.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.tarea.interfaces.Api;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;

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

	private void save(Object o) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@SuppressWarnings("unused")
	private void update(Object o) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(o);
		entityManager.refresh(o);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void registrarEstudiante(Estudiante est) {
		save(est);
	}

	@Override
	public void inscribirEstudianteCurso(Estudiante est, Curso c) {

	}

	@Override
	public List<Estudiante> buscarPorApellido(String apellido) {

		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("Select u from Estudiante u where u.persona.apellido = '"+apellido+"'");
		List list = query.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return list;
		
	}

	@Override
	public List<Curso> obtenerCursosPorEstudiante(String estId) {

		return null;
	}

	@Override
	public void registrarCurso(Curso c) {
		save(c);
	}

	@Override
	public List<Curso> obtenerCursosMenosInscriptos() {

		return null;
	}

}
