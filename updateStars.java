//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

import java.io.*;
import java.sql.*;

public class updateStars {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		updateStars star = new updateStars();
		star.addStarInfo();
	}

	public void addStarInfo() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String starname = " ";
		int starid = 20001;
	
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
while(true){
			System.out.println("Welcome manager ! Enter your star name to be entered in the database or *** to exit");
			starname = br.readLine();
			if (starname.equals("***")){
				break;
			}else
			{
			if (starname.equals("")) {
				System.out.println("Please enter a name to continue");
				starname = br.readLine();
			}
			Statement stmt = connection.createStatement();
			ResultSet stariden = stmt.executeQuery("Select MAX(STAR_ID) as maxsid from STAR");
			if (stariden.next()) {
				starid = stariden.getInt("maxsid");
				starid = starid + 1;
			}
			System.out.println(starid);
			String sql1 = "insert into STAR values (?,?) ";
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, starid);
			preparedStatement.setString(2, starname);
			int updated = preparedStatement.executeUpdate();
			
			System.out.println("New star entered: " +starname);
			System.out.println("New star id is : "  +starid);

			System.out.println("Number of rows updated=" + updated);

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
}

