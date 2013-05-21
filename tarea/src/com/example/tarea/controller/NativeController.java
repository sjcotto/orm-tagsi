package com.example.tarea.controller;

import java.util.List;

import com.example.tarea.interfaces.Api;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;

public class NativeController implements Api{

	@Override
	public void registrarEstudiante(Estudiante est) {
		
		
	}

	@Override
	public void inscribirEstudianteCurso(Estudiante est, Curso c) {
		
		
	}

	@Override
	public List<Estudiante> buscarPorApellido(String apellido) {
		
		return null;
	}

	@Override
	public List<Curso> obtenerCursosPorEstudiante(String estId) {
		
		return null;
	}

	@Override
	public void registrarCurso(Curso c) {
		
		
	}

	@Override
	public List<Curso> obtenerCursosMenosInscriptos() {
		
		return null;
	}

}
