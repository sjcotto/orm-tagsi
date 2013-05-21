package com.example.tarea.interfaces;
 
import java.util.List;

import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;

public interface Api {

	public void registrarEstudiante(Estudiante est);

	public void inscribirEstudianteCurso(Estudiante est, Curso c);

	public List<Estudiante> buscarPorApellido(String apellido);

	public List<Curso> obtenerCursosPorEstudiante(String estId);

	public void registrarCurso(Curso c);
	
	public List<Curso> obtenerCursosMenosInscriptos();
	
	
}
