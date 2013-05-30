package com.example.tarea.interfaces;
 
import java.util.List;

import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;
import com.example.tarea.model.Profesor;

public interface Api {

	public void registrarEstudiante(Estudiante est) throws Exception; //funcion 1

	


	public int registrarCurso(Curso c) throws Exception; //funcion 2
	
	public void inscribirEstudianteCurso(int est, Inscripcione c) throws Exception; //funcion 3
	

	public List<Estudiante> buscarPorApellido(String apellido) throws Exception; //funcion 4

	public List<Inscripcione> obtenerCursosPorEstudiante(int estId) throws Exception; //funcion 5
	
	public List<Curso> obtenerCursosMenosInscriptos() throws Exception;
	
	public List<Curso> obtenerCursos() throws Exception; //no esta en el doc de la parte practica
	
	public List<Profesor> obtenerProfesores() throws Exception;
	
}
