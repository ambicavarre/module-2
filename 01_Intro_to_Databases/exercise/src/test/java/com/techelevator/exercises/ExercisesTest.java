package com.techelevator.exercises;

import java.io.*;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.utilities.SqlRunner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ExercisesTest {

	private static final String SOLUTION_FOLDER = "encoded-solutions";

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	private static String exerciseFolder;
	private static List<String> exerciseFiles = new ArrayList<>();

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeAll
	public static void setupDataSource() throws SQLException, IOException, FileNotFoundException, ClassNotFoundException {
		dataSource = createDatasource("UnitedStates");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
		jdbcTemplate = new JdbcTemplate(dataSource);

		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("UnitedStates-data.sql"));

		exerciseFiles = getExerciseSqlFiles();
		if (exerciseFiles.size() == 0)
		{
			fail("Exercises folder and files not found. Please check that the Exercises folder is in the same directory or in a directory above where these tests are running from.");
		}
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterAll
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@AfterEach
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void exerciseProblem01() {
		checkAnswerForProblem("01");
	}

	@Test
	public void exerciseProblem02() {
		checkAnswerForProblem("02");
	}

	@Test
	public void exerciseProblem03() {
		checkAnswerForProblem("03");
	}

	@Test
	public void exerciseProblem04() {
		checkAnswerForProblem("04");
	}

	@Test
	public void exerciseProblem05() {
		checkAnswerForProblem("05");
	}

	@Test
	public void exerciseProblem06() {
		checkAnswerForProblem("06");
	}

	@Test
	public void exerciseProblem07() {
		checkAnswerForProblem("07");
	}

	@Test
	public void exerciseProblem08() {
		checkAnswerForProblem("08");
	}

	@Test
	public void exerciseProblem09() {
		checkAnswerForProblem("09");
	}

	@Test
	public void exerciseProblem10() {
		checkAnswerForProblem("10");
	}

	@Test
	public void exerciseProblem11() {
		checkAnswerForProblem("11");
	}

	@Test
	public void exerciseProblem12() {
		checkAnswerForProblem("12");
	}

	@Test
	public void exerciseProblem13() {
		checkAnswerForProblem("13");
	}

	@Test
	public void exerciseProblem14() {
		checkAnswerForProblem("14");
	}

	@Test
	public void exerciseProblem15() {
		checkAnswerForProblem("15");
	}

	@Test
	public void exerciseProblem16() {
		checkAnswerForProblem("16");
	}

	@Test
	public void exerciseProblem17() {
		checkAnswerForProblem("17");
	}

	@Test
	public void exerciseProblem18() {
		checkAnswerForProblem("18");
	}

	@Test
	public void exerciseProblem19() {
		checkAnswerForProblem("19");
	}

	@Test
	public void exerciseProblem20() {
		checkAnswerForProblem("20");
	}

	@Test
	public void exerciseProblem21() {
		checkAnswerForProblem("21");
	}

	@Test
	public void exerciseProblem22() {
		checkAnswerForProblem("22");
	}

	@Test
	public void exerciseProblem23() {
		checkAnswerForProblem("23");
	}

	private void checkAnswerForProblem(String problemNumber) {
		String sqlActual = getStudentQuery(problemNumber);

		sqlActual = sqlActual.replaceAll("--[^\n]*(\n|$)", "");
		assertFalse(sqlActual.isBlank(), "No query found for this exercise. Make sure you've saved your changes to the exercise file.");

		SqlRowSet sqlRSActual = jdbcTemplate.queryForRowSet(sqlActual);
		ResultSet rsActual = ((ResultSetWrappingSqlRowSet) sqlRSActual).getResultSet();

		String sqlExpected = getSolutionQuery(problemNumber);
		SqlRowSet sqlRSExpected = SqlRunner.decodeAndRunQuery(sqlExpected, jdbcTemplate);
		ResultSet rsExpected = ((ResultSetWrappingSqlRowSet) sqlRSExpected).getResultSet();

		testSuite(rsExpected, rsActual);
	}

	private void testSuite(ResultSet rsExpected, ResultSet rsActual) {

		// make sure required columns are returned by student query
		checkForRequiredColumns(rsExpected, rsActual);

		// make sure student query didn't return too many (can't be too few since we already checked for required columns)
		checkColumnCount(rsExpected, rsActual);

		// compare expected and actual row counts
		checkRowCount(rsExpected, rsActual);

		// compare expected and actual data
		compareData(rsExpected, rsActual);
	}

	private void checkForRequiredColumns(ResultSet rsExpected, ResultSet rsActual) {
		try {
			ResultSetMetaData mdExpected = rsExpected.getMetaData();
			ResultSetMetaData mdActual = rsActual.getMetaData();

			int colCountExpected = mdExpected.getColumnCount();
			int colCountActual = mdActual.getColumnCount();

			List<String> colNamesExpected = new ArrayList<>();
			List<String> colNamesActual = new ArrayList<>();

			// getColumnName() is 1-based...
			for (int i = 1; i <= colCountExpected; i++) {
				colNamesExpected.add(mdExpected.getColumnName(i));
			}
			for (int i = 1; i <= colCountActual; i++) {
				colNamesActual.add(mdActual.getColumnName(i));
			}

			for (String colNameExpected : colNamesExpected) {
				if (!colNamesActual.contains(colNameExpected)) {
					fail("Missing expected column '" + colNameExpected + "'.");
				}
			}
		} catch (SQLException ex) {
			fail(ex.getMessage());
		}
	}

	private void checkColumnCount(ResultSet rsExpected, ResultSet rsActual) {
		try {
			ResultSetMetaData mdExpected = rsExpected.getMetaData();
			ResultSetMetaData mdActual = rsActual.getMetaData();

			int colCountExpected = mdExpected.getColumnCount();
			int colCountActual = mdActual.getColumnCount();

			assertEquals(colCountExpected, colCountActual,
					"Query returns too many columns. Check your query and the problem description.");
		} catch (SQLException ex) {
			fail(ex.getMessage());
		}
	}

	private void checkRowCount(ResultSet rsExpected, ResultSet rsActual) {
		try {
			int rowCountExpected = 0;
			int rowCountActual = 0;

			rsExpected.last();
			rowCountExpected = rsExpected.getRow();
			rsExpected.beforeFirst();

			rsActual.last();
			rowCountActual = rsActual.getRow();
			rsActual.beforeFirst();

            assertEquals(rowCountExpected, rowCountActual, "Expected row count doesn't match actual row count.");
		} catch (SQLException ex) {
			fail(ex.getMessage());
		}
	}

	private void compareData(ResultSet rsExpected, ResultSet rsActual) {
		try {
			ResultSetMetaData mdExpected = rsExpected.getMetaData();
			int colCountExpected = mdExpected.getColumnCount();
			List<String> colNamesExpected = new ArrayList<>();

			// getColumnName() is 1-based...
			for (int i = 1; i <= colCountExpected; i++) {
				colNamesExpected.add(mdExpected.getColumnName(i));
			}

			while (rsExpected.next() && rsActual.next()) {
				for (String colNameExpected : colNamesExpected) {
					Object valExpected = rsExpected.getObject(colNameExpected);
					Object valActual = rsActual.getObject(colNameExpected);
					assertEquals(valExpected, valActual,
							"Data returned doesn't match data expected for row " + rsExpected.getRow() + " in column '" + colNameExpected + "'.");
				}
			}
		} catch (SQLException ex) {
			fail(ex.getMessage());
		}
	}

	private String getStudentQuery(String problemNumber) {
		String exerciseFilePath = exerciseFiles.stream()
				.filter( item ->  item.startsWith(problemNumber) ).findFirst()
				.orElse(null);
		String sql = "";

		if (exerciseFilePath == null) {
			fail("No exercise file found. Check that the file name begins with " + problemNumber + ".");
		}

		File exerciseFile = new File(exerciseFolder + "/" + exerciseFilePath);
		if (!exerciseFile.exists()) {
			fail("Exercise file doesn't exist.");
		}

		try {
			sql = Files.readString(exerciseFile.toPath());
		}
		catch (Exception e) {
			fail("Exception occurred reading exerciseFile: " + e.getMessage());
		}

		sql = sql.trim();

		return sql;
	}

	private String getSolutionQuery(String problemNumber) {
		String sql = "";
		String solutionFileName = SOLUTION_FOLDER + "/" + problemNumber + ".txt";

		try (InputStream sqlIn = ExercisesTest.class.getClassLoader().getResourceAsStream(solutionFileName);
			 BufferedReader sqlReader = new BufferedReader(new InputStreamReader(sqlIn))) {
			sql = sqlReader.lines().collect(Collectors.joining(" "));
		} catch (Exception e) {
			fail("Error opening solution file " + solutionFileName);
		}

		sql = sql.trim();

		return sql;
	}

	private static List<String> getExerciseSqlFiles() {
		String folderToFind = "Exercises";
		String currPath = System.getProperty("user.dir");
		List<String> exerFiles = new ArrayList<>();

		if (currPath.contains("\\")) {
			currPath = currPath.replace("\\", "/");
		}

		while (exerFiles.size() == 0) {
			File directoryPath = new File(currPath);

			if (directoryPath.isDirectory()) {
				File[] directories = directoryPath.listFiles((dir, name) -> name.endsWith(folderToFind));

				if (directories != null && directories.length == 1) {
					File directory = directories[0];

					exerciseFolder = directory.getAbsolutePath();

					File[] tempExerciseFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".sql"));

					for (File ef : tempExerciseFiles) {
						// get just the filenames so that we don't have to hard code the exercise file names and can find just by number
						exerFiles.add(ef.getName());
					}

					break;
				}
				else {
					if (currPath.equals("C:") || currPath.equals("/")) { // ran out of locations to check
						break;
					}

					// go up one level
					currPath = currPath.substring(0, currPath.lastIndexOf("/"));
				}
			}
		}

		return exerFiles;
	}

	private static SingleConnectionDataSource createDatasource(String defaultDbName) {
		String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
		String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
		String dbName = System.getenv("DB_DATABASE") != null ? System.getenv("DB_DATABASE") : defaultDbName;
		String username = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "postgres";
		String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "postgres1";

		String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);

		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}
}
