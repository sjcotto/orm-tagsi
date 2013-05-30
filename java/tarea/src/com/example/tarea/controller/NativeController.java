package com.example.tarea.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.tarea.interfaces.Api;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;
import com.example.tarea.model.InscripcionePK;
import com.example.tarea.model.Persona;
import com.example.tarea.model.Profesor;

public class NativeController implements Api {

	private Connection connect = null;

	private void createConnection() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");

		connect = DriverManager.getConnection("jdbc:mysql://localhost/tagsi2",
				"root", "clear300");
	}

	@SuppressWarnings("unused")
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
				java.sql.Date sqlDate = new java.sql.Date(est.getFechaIngreso()
						.getTime());
				sta.setDate(1, sqlDate);
			}
			sta.executeUpdate();
		}

		close();
	}

	@Override
	public void inscribirEstudianteCurso(int est, Inscripcione c)
			throws Exception {

		createConnection();
		String q1 = "INSERT INTO inscripciones (codigo,estudiante,fecha) VALUES (?,?,?);";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		sta.setLong(1, c.getId().getCodigo());
		sta.setLong(2, c.getId().getEstudiante());

		if (c.getFecha() != null) {
			java.sql.Date sqlDate = new java.sql.Date(c.getFecha().getTime());
			sta.setDate(3, sqlDate);

		} else {
			java.sql.Date sqlDate = new java.sql.Date((new Date()).getTime());
			sta.setDate(3, sqlDate);
		}

		sta.executeUpdate();

		sta.close();
		close();

	}

	@Override
	public List<Estudiante> buscarPorApellido(String apellido) throws Exception {

		createConnection();
		String q1 = "SELECT * FROM estudiante, persona where estudiante.id = persona.id and persona.apellido = '"
				+ apellido + "' ;";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		ResultSet rs = sta.executeQuery(q1);

		List<Estudiante> toret = new ArrayList<Estudiante>();
		while (rs.next()) {
			Estudiante c = new Estudiante();
			c.setId(rs.getInt("id"));
			c.setFechaIngreso(rs.getDate("fechaIngreso"));
			c.setMatricula(rs.getString("matricula"));

			Persona p = new Persona();
			p.setApellido(rs.getString("apellido"));
			p.setNombre(rs.getString("nombre"));
			p.setEstudiante(c);
			p.setTelefono(rs.getInt("telefono"));

			c.setPersona(p);

			toret.add(c);

		}

		rs.close();
		close();

		return toret;
	}

	@Override
	public List<Inscripcione> obtenerCursosPorEstudiante(int estId)
			throws Exception {

		//

		createConnection();
		String q1 = "SELECT * FROM inscripciones, curso where inscripciones.codigo = curso.codigo and inscripciones.estudiante = "
				+ estId + ";";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		ResultSet rs = sta.executeQuery(q1);

		List<Inscripcione> toret = new ArrayList<Inscripcione>();
		while (rs.next()) {
			Inscripcione c = new Inscripcione();

			c.setFecha(rs.getDate("fecha"));

			Curso c2 = new Curso();
			c2.setCodigo(rs.getInt("codigo"));
			c2.setNombre(rs.getString("nombre"));
			c2.setCreditos(rs.getString("creditos"));

			InscripcionePK pk = new InscripcionePK();
			pk.setCodigo(c2.getCodigo());
			pk.setEstudiante(estId);

			c.setId(pk);
			c.setCurso(c2);
			toret.add(c);

		}

		rs.close();

		close();

		return toret;

	}

	@Override
	public int registrarCurso(Curso c) throws Exception {

		//

		createConnection();
		String q1 = "insert into Curso (creditos, nombre, responsable) values (?, ?, ?);";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		sta.setString(1, c.getCreditos());
		sta.setString(2, c.getNombre());
		sta.setLong(3, c.getProfesor().getId());

		sta.executeUpdate();

		ResultSet rs = sta.getGeneratedKeys();

		int toret = 0;

		if (rs.next()) {
			toret = rs.getInt(1);
		}

		sta.close();

		close();
		return toret;

	}

	@Override
	public List<Curso> obtenerCursosMenosInscriptos() throws Exception {

		createConnection();
		String q1 = "SELECT codigo FROM estudiante e inner join inscripciones i on "
				+ "e.id = i.estudiante "
				+ "where (select count(*) from estudiante e1, inscripciones i2 where e1.id = i2.estudiante "
				+ "and e1.id = e.id) < 2 "
				+ "having count(*) > 10 and count(*) < 20;";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		ResultSet rs = sta.executeQuery(q1);

		List<Curso> toret = new ArrayList<Curso>();
		while (rs.next()) {
			Curso c = new Curso();
			c.setNombre(rs.getString("nombre"));
			c.setCreditos(rs.getString("creditos"));
			c.setCodigo(rs.getInt("codigo"));
			toret.add(c);

		}

		rs.close();

		close();
		return toret;
	}

	@Override
	public List<Curso> obtenerCursos() throws Exception {

		createConnection();
		String q1 = "select * from curso;";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		ResultSet rs = sta.executeQuery(q1);

		List<Curso> toret = new ArrayList<Curso>();
		while (rs.next()) {
			Curso c = new Curso();
			c.setNombre(rs.getString("nombre"));
			c.setCreditos(rs.getString("creditos"));
			c.setCodigo(rs.getInt("codigo"));
			toret.add(c);

		}

		rs.close();

		close();
		return toret;
	}

	@Override
	public List<Profesor> obtenerProfesores() throws Exception {
		createConnection();
		String q1 = "SELECT * FROM profesor, persona where profesor.id = persona.id;";

		PreparedStatement sta = connect.prepareStatement(q1,
				Statement.RETURN_GENERATED_KEYS);

		ResultSet rs = sta.executeQuery(q1);

		List<Profesor> toret = new ArrayList<Profesor>();
		while (rs.next()) {
			Profesor c = new Profesor();
			c.setId(rs.getInt("id"));
			Persona p = new Persona();
			p.setApellido(rs.getString("apellido"));
			p.setNombre(rs.getString("nombre"));
			p.setProfesor(c);
			c.setPersona(p);

			toret.add(c);

		}

		rs.close();

		return toret;
	}

}
