import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */

/**
 * @author cottos
 * 
 */
public class H {

	private static final int startValue = 1;
	/**
	 * @param args
	 */

	public static String q0 = "INSERT INTO persona (id,apellido,fechaNacimiento,nombre,telefono)";
	public static String q1 = "INSERT INTO estudiante(fechaIngreso,matricula,id)";

	public static String q3 = "INSERT INTO profesor(grado,id)";

	public static String q2 = "INSERT INTO curso(codigo,nombre,creditos,responsable)";

	public static void main(String[] args) {

		int countProf = 100;
		int countEst = 100;
		int countCur = 10;
		int countCurEst = 5; // cada estdiante se relaciona con 20 cursos
		int countCurProf = 5;
			
		if (args.length == 0) {

		} else {
			countEst = Integer.valueOf(args[0]);
			countProf = Integer.valueOf(args[1]);
			countCur = Integer.valueOf(args[2]);		
			countCurEst = Integer.valueOf(args[3]);
			countCurProf = Integer.valueOf(args[4]);
			
		}

		System.out.println();
		System.out.println();


		for (int i = startValue; i < countEst; i++) {

			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100000);
			String mes = Integer.toString(randomGenerator.nextInt(12));
			if (mes.length() == 1) {
				mes = "0" + mes;
			}
			String dia = Integer.toString(randomGenerator.nextInt(28));
			if (dia.length() == 1) {
				dia = "0" + dia;
			}
			String date = String.valueOf(1900 + randomGenerator.nextInt(100))
					+ "-" + mes + "-" + dia;

			// query de personas
			String ins = "VALUES(" + i + ",'apellido " + i + "','" + date
					+ "','estudiante " + i + "',"
					+ randomGenerator.nextInt(100) + ");";

			System.out.println(q0 + ins);

			// query de estudiantes
			ins = "VALUES('" + date + "','estudiante " + i + "'," + i + ");";
			System.out.println(q1 + ins);

		}

		System.out.println();
		System.out.println();

		// agregamos los profesores

		for (int i = countEst; i < countEst + countProf; i++) {

			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100000);
			String mes = Integer.toString(randomGenerator.nextInt(11)+1);
			if (mes.length() == 1) {
				mes = "0" + mes;
			}
			String dia = Integer.toString(randomGenerator.nextInt(27)+1);
			if (dia.length() == 1) {
				dia = "0" + dia;
			}
			String date = String.valueOf(1900 + randomGenerator.nextInt(100))
					+ "-" + mes + "-" + dia;

			// query de personas
			String ins = "VALUES(" + i + ",'apellido " + i + "','" + date
					+ "','profesor " + i + "'," + randomGenerator.nextInt(100)
					+ ");";

			System.out.println(q0 + ins);

			// query de profesor
			ins = "VALUES('grado " + randomGenerator.nextInt(5) + "'," + i
					+ ");";
			System.out.println(q3 + ins);

		}


		System.out.println();
		System.out.println();

		for (int i = startValue; i < countCur; i++) {
			Random randomGenerator = new Random();
			
			int num = countEst+randomGenerator.nextInt(countProf-1);
			String ins = "VALUES(" + i + ",'curso " + i + "',"
					+ String.valueOf(randomGenerator.nextInt(20)) + ","+num+");";
			System.out.println(q2 + ins);

		}
		
		System.out.println();
		System.out.println();


		for (int i = startValue; i < countEst; i++) {

			ArrayList<Integer> numbers = new ArrayList<Integer>();
			Random randomGenerator = new Random();
			while (numbers.size() < countCurEst) {

				int random = randomGenerator.nextInt(countCurEst)+1;
				if (!numbers.contains(random)) {
					numbers.add(random);
				}
			}

			for (int j = startValue; j < numbers.size(); j++) {
				String ins2 = "INSERT INTO inscripciones(codigo,estudiante,fecha)VALUES("
						+ numbers.get(j) + "," + i + ",'2012-12-12');";
				System.out.println(ins2);
			}
		}
		
		
		System.out.println();
		System.out.println();

		for (int i = countEst; i < countProf+countEst; i++) {

			ArrayList<Integer> numbers = new ArrayList<Integer>();
			Random randomGenerator = new Random();
			while (numbers.size() < countCurProf) {

				int random = randomGenerator.nextInt(countCurEst)+1;
				if (!numbers.contains(random)) {
					numbers.add(random);
				}
			}

			for (int j = startValue; j < numbers.size(); j++) {
				String ins2 = "INSERT INTO ensenan(codigo,ensenan)VALUES("
						+ numbers.get(j) + "," + i + ");";
				System.out.println(ins2);
			}
		}

	}

}
