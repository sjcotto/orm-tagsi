package com.example.tarea.controller;

import java.sql.Connection;
import java.sql.Date;
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
		connect = DriverManager.getConnection("jdbc:mysql://localhost/tagsi2",
				"root", "clear300");

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();

		// resultSet =
		// statement.executeQuery("select * from FEEDBACK.COMMENTS");

	}

	private void close() throws Exception {
		connect.close();
	}

	@Override
	public void registrarEstudiante(Estudiante est) throws Exception {
		// vienen los datos del estudiante como parametros y creamos el sql para
		// el insert

		createConnection();
		String q1 = "insert into Persona (apellido, fechaNacimiento, nombre, telefono) values (?, ?, ?, ?);";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		sta.setString(1, est.getPersona().getApellido());

		if (est.getPersona().getFechaNacimiento() != null) {
			java.sql.Date sqlDate = new java.sql.Date(est.getPersona()
					.getFechaNacimiento().getTime());
			sta.setDate(2, sqlDate);

		}

		sta.setString(3, est.getPersona().getNombre());
		sta.setInt(4, est.getPersona().getTelefono());

		sta.executeUpdate();

		ResultSet rs = sta.getGeneratedKeys();

		if (rs.next()) {
			int id = rs.getInt(1);
			String q2 = "insert into Estudiante (fechaIngreso, matricula, id) values (?, ?, ?)";
			sta = connect.prepareStatement(q2);
			sta.setInt(3, id);
			sta.setString(2, est.getMatricula());
			if (est.getFechaIngreso() != null) {
				java.sql.Date sqlDate = new java.sql.Date(est.getFechaIngreso().getTime());
				sta.setDate(1, sqlDate);
			}
			sta.executeUpdate();
		}

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
