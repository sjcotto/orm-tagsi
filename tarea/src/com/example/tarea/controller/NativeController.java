package com.example.tarea.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.example.tarea.interfaces.Api;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;

public class NativeController implements Api {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private void createConnection() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost/tagsi","root","clear300");


		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		
		//resultSet = statement.executeQuery("select * from FEEDBACK.COMMENTS");

	}

	private void close() throws Exception{
		connect.close();
	}
	
	@Override
	public void registrarEstudiante(Estudiante est) {
		//vienen los datos del estudiante como parametros y creamos el sql para el insert
		
	}

	@Override
	public void inscribirEstudianteCurso(int est, Inscripcione c) {

	}

	@Override
	public List<Estudiante> buscarPorApellido(String apellido) {

		return null;
	}

	@Override
	public List<Inscripcione> obtenerCursosPorEstudiante(int estId) {

		return null;
	}

	@Override
	public void registrarCurso(Curso c) {

	}

	@Override
	public List<Curso> obtenerCursosMenosInscriptos() {

		return null;
	}

	@Override
	public List<Curso> obtenerCursos() {
		// TODO Auto-generated method stub
		return null;
	}

}
