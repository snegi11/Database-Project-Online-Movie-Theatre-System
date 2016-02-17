//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class movieEntry {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		movieEntry movieinsert = new movieEntry();
		movieinsert.addmovie();
	}

	public void addmovie() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		String moviename = " ", movid = " ", dcprition = " ", director = " ", movid1 = " ";

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.println(
						"Welcome Manager! Lets enter movies into database -  please enter the movie name or *** to exit");
				moviename = br.readLine();
				if (moviename.equals("***")) {
					break;
				}
				String sql = "SELECT MOVIE_ID FROM MOVIE WHERE ROWNUM = 1 ORDER BY MOVIE_ID DESC";
				preparedStatement = connection.prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					movid1 = rs.getString(1);
				}
				System.out.println("Please enter a movie id in format (M-- ) the last movie id entered was " + movid1);
				movid = br.readLine();
				if (movid.equals("")) {
					System.out.println("Please enter a valid movie ID  to continue");
					movid = br.readLine();
				}
				System.out.println("Please enter the movie description");
				dcprition = br.readLine();
				if (dcprition.equals("")) {
					System.out.println("Please enter a valid description to continue");
					dcprition = br.readLine();
				}
				System.out.println("Please enter the movie director");
				director = br.readLine();
				if (director.equals("")) {
					System.out.println("Please enter a valid director to continue");
					director = br.readLine();
				}
				String sql1 = "INSERT INTO MOVIE values (?,?,?,?)";
				preparedStatement2 = connection.prepareStatement(sql1);
				preparedStatement2.setString(1, movid);
				preparedStatement2.setString(2, moviename);
				preparedStatement2.setString(3, dcprition);
				preparedStatement2.setString(4, director);
				int updated = preparedStatement2.executeUpdate();
				System.out.println("No of rows updated in movie table" + updated);
				System.out.println("Do you want to add movie genre too ? Y/N ");
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				String input = br1.readLine();
				if (input.toLowerCase().equals("y")) {
					addgenre(movid);
				} else {
					System.out.println(
							"Either you not interested in adding movie genre or you have pressed the wrong key ! Bye Bye ! ");
				}

			}

		} catch (SQLException se) {
			/*
			 * Handle errors for JDBC
			 */
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*
			 * finally block used to close resources
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

		}

	}

	public void addgenre(String movid) {
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement prepapredstatementgenre = null;
		String types = " ", typemovie = " ";
		int typei = 0;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			System.out.println("Welcome user these are the following types available !");
			String sqltype = "Select * from movie_type";
			preparedStatement1 = connection.prepareStatement(sqltype);
			ResultSet rs3 = preparedStatement1.executeQuery();
			while (rs3.next()) {
				types = rs3.getString(2);
				typei = rs3.getInt(1);
				System.out.println(typei + "  " + types);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println(
						"Select any of the movie types above and use the corresponding type id to enter else type *** to exit");
				typemovie = br.readLine();

				if (typemovie.equals("***")) {
					break;
				}
				System.out.println(typemovie);

				String sqlinsertgenre = "Insert into movie_genre values (?,?)";
				prepapredstatementgenre = connection.prepareStatement(sqlinsertgenre);
				prepapredstatementgenre.setString(1, movid);
				prepapredstatementgenre.setString(2, typemovie);
				prepapredstatementgenre.executeUpdate();

			}
			System.out.println("Do you want to add movie cast too ? Y/N ");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			String input = br1.readLine();
			if (input.toLowerCase().equals("y")) {
				addmoviecast(movid);
			} else {
				System.out.println(
						"Either you not interested in adding movie genre or you have pressed the wrong key ! Bye Bye ! ");
			}

		} catch (SQLException se) {
			/*
			 * Handle errors for JDBC
			 */
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*
			 * finally block used to close resources
			 */
			try {
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				}

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

	}

	public void addmoviecast(String movid) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatementupdate = null;
		PreparedStatement preparedStatement2 = null;
		String starID = " ", starid1 = " " , starname1 = " ";

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Welcome user these are names of stars in our database !");
			String sqlstar = "Select * from star order by star_name";
			preparedStatement2 = connection.prepareStatement(sqlstar);
			ResultSet rsstar = preparedStatement2.executeQuery();
			while (rsstar.next()) {
				starname1 = rsstar.getString(2);
				starid1 = rsstar.getString(1);
				System.out.println(starid1 + " "+ starname1);
			}
			
			
			while (true) {
				System.out.println("Please Enter the StarID corresponding to StarName to be inserted or 0 to exit");
				starID = br1.readLine();
				if (starID.equals("0")) {
					break;
				}
				
					System.out.println("star id is: " + starID);
					String sql3 = "Insert into MOVIE_CAST values (?,?) ";
					preparedStatementupdate = connection.prepareStatement(sql3);
					preparedStatementupdate.setString(1, movid);
					preparedStatementupdate.setString(2, starID);
					preparedStatementupdate.executeUpdate();

				
				
		
			}
		} catch (SQLException se) {
			/*
			 * Handle errors for JDBC
			 */
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*
			 * finally block used to close resources
			 */
			try {
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				}

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

		}

	}

}
