package com.example.tarea.test;

import java.util.Date;

import com.example.tarea.interfaces.Api;
import com.example.tarea.interfaces.Factory;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;
import com.example.tarea.model.InscripcionePK;
import com.example.tarea.model.Persona;
import com.example.tarea.model.Profesor;

public class Test {

	private static final int LIMIT = 1000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// viene un unico parametro desde la entrada que indica el numero de
		// prueba a ejecutar

		long tiempoInicio = System.currentTimeMillis();

		int nro = 1;
		if (args.length > 0) {
			nro = Integer.valueOf(args[0]);
		}

		nro = 12;

		if (nro == 1) {
			ejecutarTest1Nativo(false);
		} else if (nro == 2) {
			ejecutarTest2Nativo(false);
		} else if (nro == 3) {
			ejecutarTest3Nativo(false);
		} else if (nro == 4) {
			ejecutarTest4Nativo(false);
		} else if (nro == 5) {
			ejecutarTest5Nativo(false);
		} else if (nro == 6) {
			ejecutarTest6Nativo(false);
		} else if (nro == 7) { // test 1 con ORM
			ejecutarTest1Nativo(true);
		} else if (nro == 8) { // test 2 con ORM
			ejecutarTest2Nativo(true);
		} else if (nro == 9) { // test 3 con ORM
			ejecutarTest3Nativo(true);
		} else if (nro == 10) {
			ejecutarTest4Nativo(true);// test 4 con ORM
		} else if (nro == 11) {
			ejecutarTest5Nativo(true);// test 5 con ORM
		} else if (nro == 12) {
			ejecutarTest6Nativo(true);// test 6 con ORM
		}

		
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;

		System.out.println("time :" + totalTiempo + " miliseg");

	}

	private static void ejecutarTest6Nativo(boolean b) {
		// TODO Auto-generated method stub

		Api api = Factory.getInstance().setOrm(b).getController();
		try {

			for (int i = 1; i < LIMIT; i++) {
				api.obtenerCursosMenosInscriptos();
				System.out.println(i);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ejecutarTest5Nativo(boolean b) {
		// obtener los cursos a los que esta inscripto un estudiante

		Api api = Factory.getInstance().setOrm(b).getController();
		try {

			for (int i = 1; i < LIMIT; i++) {
				api.obtenerCursosPorEstudiante(1);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ejecutarTest4Nativo(boolean b) {
		// buscamos estudiantes por apellido (mil veces)

		Api api = Factory.getInstance().setOrm(b).getController();

		try {

			for (int i = 1; i < LIMIT; i++) {
				api.buscarPorApellido("apellido 5");
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ejecutarTest3Nativo(boolean b) {
		// realizar la inscripcion de un estudiante a un curso
		// tomamos 1 estudiante, por ejemplo el 1 y lo inscribimos a un curso
		// (mil veces?) se puede?

		// primero creamos el curso para las pruebas

		Api api = Factory.getInstance().setOrm(b).getController();

		int codigo = crearUnCurso();
		Inscripcione ins = new Inscripcione();
		InscripcionePK pk = new InscripcionePK();

		try {

			for (int i = 1; i < LIMIT; i++) {
				pk.setCodigo(codigo);
				pk.setEstudiante(i);
				ins.setId(pk);
				api.inscribirEstudianteCurso(1, ins);
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int crearUnCurso() {
		Api api = Factory.getInstance().setOrm(false).getController();

		Profesor p;
		try {
			p = api.obtenerProfesores().get(0);
			Curso c = new Curso();
			c.setCreditos("123");
			c.setNombre("un curso");
			c.setProfesor(p);

			return api.registrarCurso(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

	private static void ejecutarTest2Nativo(boolean b) {
		// registrar un nuevo curso (1000 veces)

		// los asociamos a todos al mismo profesor

		Api api = Factory.getInstance().setOrm(b).getController();
		try {
			Profesor p = api.obtenerProfesores().get(0);
			for (int i = 1; i < LIMIT; i++) {

				
				Curso c = new Curso();
				c.setCreditos(Integer.toString(i));
				c.setNombre("nombre test " + i);
				c.setProfesor(p);

				api.registrarCurso(c);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void ejecutarTest1Nativo(boolean b) {
		// Registrar un nuevo estudiante

		Api api = Factory.getInstance().setOrm(b).getController();
		for (int i = 0; i < LIMIT; i++) {
			
			Estudiante est = new Estudiante();
			est.setFechaIngreso(new Date());
			est.setMatricula("matricula ");

			Persona p = new Persona();
			p.setNombre("test est " + i);
			p.setApellido("test est");
			p.setFechaNacimiento(new Date());

			est.setPersona(p);
			try {
				api.registrarEstudiante(est);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

}
