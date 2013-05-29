package com.example.tarea.interfaces;
 
import java.util.List;

import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;

public interface Api {

	public void registrarEstudiante(Estudiante est); //funcion 1

	


	public void registrarCurso(Curso c); //funcion 2
	
	public void inscribirEstudianteCurso(int est, Inscripcione c); //funcion 3
	

	public List<Estudiante> buscarPorApellido(String apellido); //funcion 4

	public List<Inscripcione> obtenerCursosPorEstudiante(int estId); //funcion 5
	
	public List<Curso> obtenerCursosMenosInscriptos();
	
	public List<Curso> obtenerCursos(); //no esta en el doc de la parte practica
	
}
